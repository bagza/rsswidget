package com.example.gryazin.rsswidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import com.example.gryazin.rsswidget.data.services.UpdateScheduler;

import javax.inject.Inject;

/**
 * Created by bag on 15.11.16.
 */

public class RssWigdetProvider extends AppWidgetProvider {

    @Inject
    UpdateScheduler updateScheduler;

    private void inject(){
        RssApplication.getAppComponent().inject(this);
    }

    @Override
    public void onUpdate(Context ctxt, AppWidgetManager mgr, int[] appWidgetIds) {
        inject();
        for (int appWidgetId : appWidgetIds){
            updateScheduler.refreshWidgetNowAndScheduleUpdates(appWidgetId);
        }
    }
}
