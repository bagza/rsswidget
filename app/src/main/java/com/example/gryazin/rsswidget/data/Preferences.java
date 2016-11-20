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
    private final static String FREQ_KEY = "freq";
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

    public boolean hasFreq(){
        return sharedPreferences.contains(FREQ_KEY);
    }

    public long getUpdateFreq(){
        Utils.debugAssert(hasFreq());
        return sharedPreferences.getLong(FREQ_KEY, 0L);
    }

    public void storeUpdateFreq(long value){
        sharedPreferences.edit()
                .putLong(FREQ_KEY, value)
                .apply();
    }
}
