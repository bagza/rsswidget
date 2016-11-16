package com.example.gryazin.rsswidget.di;

import android.app.Application;
import android.content.Context;

import com.example.gryazin.rsswidget.RssApplication;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Zver on 16.11.2016.
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
}
