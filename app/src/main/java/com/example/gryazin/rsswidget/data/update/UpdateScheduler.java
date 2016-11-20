package com.example.gryazin.rsswidget.data.update;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
    private static long TEN_SEC_MS = 10 * 1000L;
    public static Long pollPeriodMs = TEN_SEC_MS;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Context context;
    @Inject
    public UpdateScheduler(Context context) {
        this.context = context;
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, RssAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //TEST
                refreshFetchAndReschedule();
                Toast.makeText(RssApplication.getContext(), "widget update", Toast.LENGTH_SHORT).show();
                handler.postDelayed(this, 7* 1000L);
            }
        }, 7* 1000L);*/
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
    }

    public void refreshFetchAndReschedule(){
        cancelFetchAlarm();
        setFetchAlarm();
        Intent intent = new Intent(context, NetworkFetchService.class);
        context.startService(intent);
    }

    public void setFetchAlarm() {
        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + pollPeriodMs, alarmIntent);
        enableRebootStartUp();
    }

    public void cancelFetchAlarm() {
        alarmMgr.cancel(alarmIntent);
        disableRebootStartUp();
    }

    private void enableRebootStartUp(){
        ComponentName receiver = new ComponentName(context, RebootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void disableRebootStartUp(){
        ComponentName receiver = new ComponentName(context, RebootReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }
}
