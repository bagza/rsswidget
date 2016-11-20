package com.example.gryazin.rsswidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

import com.example.gryazin.rsswidget.data.Repository;
import com.example.gryazin.rsswidget.data.update.UpdateScheduler;

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

    @Override
    public void onUpdate(Context ctxt, AppWidgetManager mgr, int[] appWidgetIds) {
        inject();

        for (int appWidgetId : appWidgetIds){
            updateScheduler.refreshWidget(appWidgetId);
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
