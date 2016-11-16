package com.example.gryazin.rsswidget.utils;

import com.example.gryazin.rsswidget.BuildConfig;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public static String[] args(Object... args) {
        String[] ret = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            ret[i] = args[i].toString();
        }
        return ret;
    }

    public static String readFileAsString(InputStream inputStream)
            throws java.io.IOException {
        try {
            final int bufsize = 4096;
            int available = inputStream.available();
            byte[] data = new byte[available < bufsize ? bufsize : available];
            int used = 0;
            while (true) {
                if (data.length - used < bufsize) {
                    byte[] newData = new byte[data.length << 1];
                    System.arraycopy(data, 0, newData, 0, used);
                    data = newData;
                }
                int got = inputStream.read(data, used, data.length - used);
                if (got <= 0) break;
                used += got;
            }
            return new String(data, 0, used);
        } finally {
            inputStream.close();
        }
    }
}
