package com.example.gryazin.rsswidget;

/**
 * Created by Zver on 16.11.2016.
 */

public class Utils {
    public static void debugAssert(boolean condition){
        if (BuildConfig.DEBUG && !(condition))
        { throw new AssertionError(); }
    }

    public static void debugAssert(boolean condition, String msg){
        if (BuildConfig.DEBUG && !(condition))
        { throw new AssertionError(msg); }
    }
}
