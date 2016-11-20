package com.example.gryazin.rsswidget.data.update;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.example.gryazin.rsswidget.RssApplication;
import com.example.gryazin.rsswidget.data.network.NetworkFetchService;

import javax.inject.Inject;

/**
 * Created by Dmitry Gryazin on 20.11.2016.
 */

public class RssAlarmReceiver extends WakefulBroadcastReceiver {
    @Inject
    public UpdateScheduler updateScheduler;

    @Override
    public void onReceive(Context context, Intent intent) {
        RssApplication.getAppComponent().inject(this);
        Intent service = new Intent(context, NetworkFetchService.class);
        startWakefulService(context, service);
        updateScheduler.setFetchAlarm();
    }
}
