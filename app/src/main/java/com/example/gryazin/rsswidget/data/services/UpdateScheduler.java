package com.example.gryazin.rsswidget.data.services;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.example.gryazin.rsswidget.RssApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class UpdateScheduler {
    private Context context;
    private List<Integer> ids = new ArrayList<>();
    @Inject
    public UpdateScheduler(Context context) {
        this.context = context;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int id : ids){
                    refreshWidgetNowAndScheduleUpdates(id);
                }
                Toast.makeText(RssApplication.getContext(), "widget update", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, 7* 1000L);
            }
        }, 7* 1000L);
    }

    public void refreshWidgetNowAndScheduleUpdates(int appWidgetId){
        if (!ids.contains(appWidgetId)) ids.add(appWidgetId);
        Intent intent = new Intent(context, WidgetsRefreshService.class);
        intent.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setAction(WidgetsRefreshService.ACTION_REGULAR_REFRESH);
        context.startService(intent);
        //TODO ALARMS!!
    }

    public void cancel(int appWidgetId){
        for(int i=0; i < ids.size(); i++){
            if (ids.get(i) == appWidgetId)
            ids.remove(i);
            break;
        }
    }
}
