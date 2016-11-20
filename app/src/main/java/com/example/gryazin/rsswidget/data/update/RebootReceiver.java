package com.example.gryazin.rsswidget.data.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.gryazin.rsswidget.RssApplication;

/**
 * Created by Dmitry Gryazin on 20.11.2016.
 */

public class RebootReceiver extends BroadcastReceiver {
    public UpdateScheduler updateScheduler;
    @Override
    public void onReceive(Context context, Intent intent) {
        RssApplication.getAppComponent().inject(this);
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            updateScheduler.setFetchAlarm();
        }
    }
}
