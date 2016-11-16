package com.example.gryazin.rsswidget.data.services;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class UpdateScheduler {
    private Context context;

    @Inject
    public UpdateScheduler(Context context) {
        this.context = context;
    }

    public void refreshWidgetNowAndScheduleUpdates(int appWidgetId){
        Intent intent = new Intent(context, WidgetsRefreshService.class);
        intent.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setAction(WidgetsRefreshService.ACTION_REGULAR_REFRESH);
        context.startService(intent);
        //TODO ALARMS!!
    }
}
