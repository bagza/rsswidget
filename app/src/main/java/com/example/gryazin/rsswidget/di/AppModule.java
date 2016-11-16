package com.example.gryazin.rsswidget.di;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

@Module(includes = {
        //ApiModule.class,
        DataModule.class
})
public final class AppModule {
    private final Application app;
    public AppModule(Application app) {
        this.app = app;
    }

    @Provides @Singleton
    Application provideApplicationContext() {
        return app;
    }

    @Provides @Singleton
    Context provideContext() {
        return app;
    }

    @Provides
    AppWidgetManager provideAppWidgetManager(Context context){
        return AppWidgetManager.getInstance(context);
    }
}