package com.example.gryazin.rsswidget.di;

import android.app.Application;
import android.content.Context;

import com.example.gryazin.rsswidget.RssApplication;
import com.example.gryazin.rsswidget.RssWigdetProvider;
import com.example.gryazin.rsswidget.SettingsActivity;
import com.example.gryazin.rsswidget.data.Repository;
import com.example.gryazin.rsswidget.data.network.NetworkFetchService;
import com.example.gryazin.rsswidget.data.update.RebootReceiver;
import com.example.gryazin.rsswidget.data.update.RssAlarmReceiver;
import com.example.gryazin.rsswidget.data.update.WidgetsRefreshService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */
@Singleton
@Component(modules = {
        AppModule.class
})
public interface AppComponent {
    void inject(RssApplication app);

    Application getApplicationContext();
    Context getContext();

    Repository provideRepository();

    void inject(WidgetsRefreshService service);
    void inject(RssWigdetProvider provider);
    void inject(SettingsActivity settingsActivity);
    void inject(NetworkFetchService service);
    void inject(RssAlarmReceiver receiver);
    void inject(RebootReceiver receiver);
}
