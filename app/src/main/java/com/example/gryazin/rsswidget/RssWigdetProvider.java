package com.example.gryazin.rsswidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

import com.example.gryazin.rsswidget.data.Repository;
import com.example.gryazin.rsswidget.data.services.UpdateScheduler;

import javax.inject.Inject;

/**
 * Created by bag on 15.11.16.
 */

public class RssWigdetProvider extends AppWidgetProvider {

    @Inject
    UpdateScheduler updateScheduler;
    @Inject
    Repository repository;

    private void inject(){
        RssApplication.getAppComponent().inject(this);
    }

    /**
     * I think the doc lies that "the system will not send the ACTION_APPWIDGET_UPDATE broadcast when a configuration Activity is launched".
     * I DO recieve it on genymotion. And that's sad.
     *
     * Need to ignore the first update OR handle expections somewhere.
     *
     * @param ctxt
     * @param mgr
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context ctxt, AppWidgetManager mgr, int[] appWidgetIds) {
        inject();

        for (int appWidgetId : appWidgetIds){
            updateScheduler.refreshWidgetNowAndScheduleUpdates(appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        inject();

        for (int id : appWidgetIds){
            updateScheduler.cancel(id);
            repository.deleteSettings(id);
        }
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        inject();

        //TODO cancel all and delete data
    }
}
