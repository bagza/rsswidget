package com.example.gryazin.rsswidget.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gryazin.rsswidget.R;
import com.example.gryazin.rsswidget.utils.Utils;

import javax.inject.Inject;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class Preferences {
    private final static String TIMESTAMP_KEY = "timestamp";
    private Context context;
    private SharedPreferences sharedPreferences;

    @Inject
    public Preferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file_name), Context.MODE_PRIVATE);
    }

    public void clear(){
        sharedPreferences.edit()
                .clear()
                .apply();
    }

    public boolean hasTimestamp(){
        return sharedPreferences.contains(TIMESTAMP_KEY);
    }

    public long getUpdateTimestamp(){
        Utils.debugAssert(hasTimestamp());
        return sharedPreferences.getLong(TIMESTAMP_KEY, 0L);
    }

    public void storeUpdateTimestamp(long value){
        sharedPreferences.edit()
                .putLong(TIMESTAMP_KEY, value)
                .apply();
    }
}
