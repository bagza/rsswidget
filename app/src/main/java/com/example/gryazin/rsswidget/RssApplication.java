package com.example.gryazin.rsswidget;

import android.app.Application;
import android.content.Context;

import com.example.gryazin.rsswidget.di.AppComponent;
import com.example.gryazin.rsswidget.di.AppModule;
import com.example.gryazin.rsswidget.di.DaggerAppComponent;

import javax.inject.Inject;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class RssApplication extends Application{
    private static Context context;
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initializeDependencyInjector();
    }

    private void initializeDependencyInjector() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(this))
                    .build();
            appComponent.inject(this);
        }
    }

    //Needed for activities to get the entry point for injection!
    public static RssApplication get(Context context) {
        return (RssApplication) context.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
    public static AppComponent getAppComponent(){
        return appComponent;
    }
}
