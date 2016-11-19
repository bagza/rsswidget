package com.example.gryazin.rsswidget.data.update;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.example.gryazin.rsswidget.RssApplication;
import com.example.gryazin.rsswidget.data.network.NetworkFetchService;

import javax.inject.Inject;

import static android.appwidget.AppWidgetManager.EXTRA_APPWIDGET_ID;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class UpdateScheduler {
    private static long ONE_MINUTE_MS = 1 * 60 * 1000L;
    public static Long pollPeriodMs = ONE_MINUTE_MS;

    private Context context;
    @Inject
    public UpdateScheduler(Context context) {
        this.context = context;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //TEST
                refreshFetch();
                Toast.makeText(RssApplication.getContext(), "widget update", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, 7* 1000L);
            }
        }, 7* 1000L);
    }

    public void refreshAllWidgets(int[] appWidgetIds){
        for (int id : appWidgetIds){
            refreshWidget(id);
        }
    }

    public void refreshWidget(int appWidgetId){
        Intent intent = new Intent(context, WidgetsRefreshService.class);
        intent.putExtra(EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setAction(WidgetsRefreshService.ACTION_REGULAR_REFRESH);
        context.startService(intent);
        //TODO ALARMS!!
    }

    public void cancel(int appWidgetId){

    }

    public void refreshFetch(){
        Intent intent = new Intent(context, NetworkFetchService.class);
        context.startService(intent);
    }
}
