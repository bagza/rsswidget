package com.example.gryazin.rsswidget.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.example.gryazin.rsswidget.R;
import com.example.gryazin.rsswidget.data.Repository;
import com.example.gryazin.rsswidget.data.WidgetState;
import com.example.gryazin.rsswidget.domain.FeedItem;
import com.example.gryazin.rsswidget.domain.UpdateStatus;
import com.example.gryazin.rsswidget.utils.Utils;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;

/**
 * Created by Dmitry Gryazin on 18.11.2016.
 * WISDOM: all calls are synchronous, because all job is done in background by services.
 */
//TODO test this class, check VM creation
public class WidgetPresenter {

    private Context context;
    private Repository repository;
    private RemoteViewsRenderer renderer;

    @Inject
    public WidgetPresenter(Context context, Repository repository, RemoteViewsRenderer renderer) {
        this.context = context;
        this.repository = repository;
        this.renderer = renderer;
    }

    public RemoteViews onRefresh(int widgetId){
        FeedViewModel viewModel = bakeViewModel(widgetId, 0);
        return renderer.getRenderedViews(widgetId, viewModel);
    }

    public RemoteViews onNext(int widgetId){
        FeedViewModel viewModel = bakeViewModel(widgetId, 1);
        return renderer.getRenderedViews(widgetId, viewModel);
    }

    public RemoteViews onPrev(int widgetId){
        FeedViewModel viewModel = bakeViewModel(widgetId, -1);
        return renderer.getRenderedViews(widgetId, viewModel);
    }

    private FeedViewModel bakeViewModel(int widgetId, int positionOffset){
        return getUpdateStatus().accept(new UpdateStatus.UpdateStatusVisitor<FeedViewModel>() {
            @Override
            public FeedViewModel onSuccess(long timestamp) {
                return bakeSuccessFeedModelAndSaveWidgetState(widgetId, positionOffset, timestamp);
            }

            @Override
            public FeedViewModel onEmpty() {
                return bakeEmptyModel();
            }

            @Override
            public FeedViewModel onError(String message) {
                return bakeErrorModel(message);
            }
        });
    }

    private FeedViewModel bakeEmptyModel(){
        return FeedViewModel.Builder.builder()
                .withBody("")
                .withTitle(context.getString(R.string.feed_empty_title))
                .build();
    }

    private FeedViewModel bakeErrorModel(String message){
        return FeedViewModel.Builder.builder()
                .withBody(message)
                .withTitle(context.getString(R.string.feed_error_title))
                .build();
    }

    //refactor note - widget saving is unobvious and unwanted here
    private FeedViewModel bakeSuccessFeedModelAndSaveWidgetState(int widgetId, int positionOffset, long timestamp){
        TreeSet<? extends FeedItem> feedItems = getFeeds(widgetId);

        FeedItem feedToShow = getFeedToShow(feedItems, widgetId, positionOffset);
        if (feedToShow == null){
            return FeedViewModel.Builder.builder()
                    .withBody("")
                    .withTitle(context.getString(R.string.feed_empty_title))
                    .withTimestamp(timestamp)
                    .build();
        }
        int itemPosition = getItemPosOrGetLastPos(feedItems, feedToShow.getGuid());
        saveWidgetState(new WidgetState.StateLastWatched(widgetId, itemPosition, feedToShow.getGuid()));
        return FeedViewModel.Builder.builder()
                .byFeedItem(feedToShow)
                .withTimestamp(timestamp)
                .withNext(itemPosition < feedItems.size() - 1)
                .withPrev(itemPosition > 0)
                .build();
    }

    @Nullable
    private FeedItem getFeedToShow(TreeSet<? extends FeedItem> feedItems, int widgetId, int positionOffset){
        if (feedItems.isEmpty()){
            //NOTHING TO SHOW, though the update should be ok. That's strange but possible.
            //TODO refactor note: better use scpecial case object, not the best place for NULL
            return null;
        }

        return getLastWidgetState(widgetId).accept(new WidgetState.Visitor<FeedItem>() {
            @Override
            public FeedItem onLastWatched(int lastWatchedPosition, String lastWatchedFeedGuid) {
                if (lastWatchedPosition == 0 && positionOffset == 0){
                    return feedItems.first();
                }
                else if (positionOffset == 0){
                    return feedItems.stream()
                            .filter((feed) -> feed.getGuid().equals(lastWatchedFeedGuid))
                            .findFirst()
                            .orElse(null);
                }
                else if (positionOffset != 0){
                    int lastPosition = getItemPosOrGetLastPos(feedItems, lastWatchedFeedGuid);
                    int newPosition = lastPosition + positionOffset;
                    Utils.debugAssert(newPosition >= 0 || newPosition < feedItems.size());
                    //trick to get Kth element with streams
                    return feedItems.stream()
                            .limit(newPosition + 1)
                            .reduce((a, b) -> b)
                            .orElse(null);
                }
                return null;
            }

            @Override
            public FeedItem onEmptyWatchedState() {
                return feedItems.first();
            }
        });
    }

    private int getItemPosOrGetLastPos(TreeSet<? extends FeedItem> feedItems, String lastWatchedFeedGuid){
        Iterator<? extends FeedItem> iterator = feedItems.iterator();
        int i = 0;
        while (iterator.hasNext()){
            if (iterator.next().getGuid().equals(lastWatchedFeedGuid)){
                return i;
            }
            i++;
        }
        // no such guid found... it's not there anymore??
        return feedItems.size() - 1;
    }

    private TreeSet<? extends FeedItem> getFeeds(int widgetId){
        return repository.getAllFeedsByWidgetId(widgetId);
    }

    private WidgetState getLastWidgetState(int widgetId){
        return repository.getWidgetStateById(widgetId);
    }

    private void saveWidgetState(WidgetState widgetState){
        repository.saveWidgetState(widgetState);
    }

    private SortedSet<? extends FeedItem> getWidgetFeeds(int widgetId){
        return repository.getAllFeedsByWidgetId(widgetId);
    }

    private UpdateStatus getUpdateStatus(){
        return repository.getUpdateStatus();
    }
}
