package com.example.gryazin.rsswidget.data.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.gryazin.rsswidget.R;
import com.example.gryazin.rsswidget.RssApplication;
import com.example.gryazin.rsswidget.data.Repository;
import com.example.gryazin.rsswidget.domain.FeedItem;
import com.example.gryazin.rsswidget.ui.FeedViewModel;
import com.example.gryazin.rsswidget.domain.UpdateStatus;
import com.example.gryazin.rsswidget.ui.WidgetPresenter;

import java.util.Date;
import java.util.SortedSet;

import javax.inject.Inject;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;
import static android.appwidget.AppWidgetManager.INVALID_APPWIDGET_ID;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 *
 * Task to update single widget
 */
public class WidgetsRefreshService extends IntentService {

    public static final String ACTION_REGULAR_REFRESH = "refresh";
    public static final String ACTION_SHOW_NEXT = "show_next";
    public static final String ACTION_SHOW_PREV = "show_prev";

    @Inject
    AppWidgetManager appWidgetManager;
    @Inject
    Repository repository;
    @Inject
    WidgetPresenter presenter;

    public WidgetsRefreshService() {
        super("WidgetsRefreshService");
        RssApplication.getAppComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            int appWidgetId = intent.getIntExtra(EXTRA_APPWIDGET_ID, INVALID_APPWIDGET_ID);
            if (appWidgetId != INVALID_APPWIDGET_ID) {
                updateAppWidget(appWidgetId, intent);
            }
        }
        catch (IllegalStateException e){
            //means there was illegal update call to receiver while the settings were not set yet.
            //See Receiver comments also
            Log.d("ILLEGAL UPDATE", e.getLocalizedMessage());
        }
    }

    private void updateAppWidget(int appWidgetId, Intent intent){
        RemoteViews remoteViews;
        switch (intent.getAction()){
            case ACTION_SHOW_NEXT:
                remoteViews = presenter.onNext(appWidgetId);
                break;
            case ACTION_SHOW_PREV:
                remoteViews = presenter.onPrev(appWidgetId);
                break;
            default:
                remoteViews = presenter.onRefresh(appWidgetId);
        }
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}
