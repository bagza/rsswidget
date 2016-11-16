package com.example.gryazin.rsswidget.di;

import com.example.gryazin.rsswidget.data.FakeRepository;
import com.example.gryazin.rsswidget.data.Preferences;
import com.example.gryazin.rsswidget.data.Repository;
import com.example.gryazin.rsswidget.data.db.DBHelper;
import com.example.gryazin.rsswidget.data.db.RssDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

@Module
public class DataModule {
    /*@Provides @Singleton
    DBHelper provideDBHelper(Context context){
        return new DBHelper(context);
    }

    @Provides @Singleton
    LectoriyDB provideLectoriyDB(Context context, DBHelper dbHelper){
        return new LectoriyDB(context, dbHelper);
    }*/

    @Provides @Singleton
    Repository provideRepository(RssDatabase database, Preferences preferences){
        return new FakeRepository(database, preferences);
    }
}