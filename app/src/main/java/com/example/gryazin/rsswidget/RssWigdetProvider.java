package com.example.gryazin.rsswidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

/**
 * Created by bag on 15.11.16.
 */

public class RssWigdetProvider extends AppWidgetProvider {

    //TODO make injection


    @Override
    public void onUpdate(Context ctxt, AppWidgetManager mgr,
                         int[] appWidgetIds) {
        ComponentName widgetComponent=new ComponentName(ctxt, RssWigdetProvider.class);
        mgr.updateAppWidget(widgetComponent, buildUpdate(ctxt, appWidgetIds));
    }

    private RemoteViews buildUpdate(Context ctxt, int[] appWidgetIds) {
        RemoteViews updateViews=new RemoteViews(ctxt.getPackageName(),
                R.layout.single_item_rss_widget);
        /*Intent i=new Intent(ctxt, AppWidget.class);
        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        PendingIntent pi=PendingIntent.getBroadcast(ctxt, 0 , i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setImageViewResource(R.id.left_die,
                IMAGES[(int)(Math.random()*6)]);
        updateViews.setOnClickPendingIntent(R.id.left_die, pi);*/

        return(updateViews);
    }
}
