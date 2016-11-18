package com.example.gryazin.rsswidget.ui;

import android.widget.RemoteViews;

import com.example.gryazin.rsswidget.data.Repository;
import com.example.gryazin.rsswidget.data.WidgetState;
import com.example.gryazin.rsswidget.domain.FeedItem;

import java.util.SortedSet;

/**
 * Created by Dmitry Gryazin on 18.11.2016.
 * WISDOM: all calls are synchronous, because all job is done in background by services.
 */

public class WidgetPresenter {
    private Repository repository;

    public RemoteViews onRefresh(int widgetId){

    }

    public RemoteViews onNext(int widgetId){

    }

    public RemoteViews onPrev(int widgetId){

    }

    private WidgetState getWidgetState(int widgetId){
        //TODO Stopped here. Add widget states and test read write.
        return repository.get
    }

    private SortedSet<? extends FeedItem> getWidgetFeeds(int widgetId){
        return repository.getAllFeedsByWidgetId(widgetId);
    }
}
