package com.example.gryazin.rsswidget.ui;

import android.content.Context;
import android.widget.RemoteViews;

import com.example.gryazin.rsswidget.R;
import com.example.gryazin.rsswidget.domain.FeedItem;
import com.example.gryazin.rsswidget.domain.UpdateStatus;

import java.util.Date;

import javax.inject.Inject;

/**
 * Created by Dmitry Gryazin on 17.11.2016.
 */

public class RemoteViewsRenderer {
    private Context context;

    @Inject
    public RemoteViewsRenderer(Context context) {
        this.context = context;
    }

    public RemoteViews getRenderedViews(FeedViewModel viewModel){
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.single_item_rss_widget);
        renderViewsByUpdateStatus(remoteViews, viewModel);
        return remoteViews;
    }

    private void renderViewsByUpdateStatus(RemoteViews remoteViews, FeedViewModel viewModel){
        viewModel.getUpdateStatus().accept(new UpdateStatus.UpdateStatusVisitor<Void>() {
            @Override
            public Void onSuccess(long timestamp) {
                renderViewsWithFeed(remoteViews, viewModel.getFeedItem(), timestamp);
                return null;
            }

            @Override
            public Void onEmpty() {
                remoteViews.setTextViewText(R.id.text_title, "EMPTY");
                return null;
            }

            @Override
            public Void onError(String message) {
                remoteViews.setTextViewText(R.id.text_title, "ERROR");
                return null;
            }
        });
    }

    private void renderViewsWithFeed(RemoteViews remoteViews, FeedItem feedItem, long timestamp){
        remoteViews.setTextViewText(R.id.text_title, feedItem.getTitle());
        remoteViews.setTextViewText(R.id.text_subtitle, new Date(timestamp).toString());
        remoteViews.setTextViewText(R.id.text_body, feedItem.getDescription());
    }
}
