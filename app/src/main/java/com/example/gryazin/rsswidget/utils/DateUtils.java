package com.example.gryazin.rsswidget.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class DateUtils {

    private static int year = 2000;

    public static Date randomDate(){
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(calendar.YEAR, year++);
        int dayOfYear = randBetween(1, calendar.getActualMaximum(calendar.DAY_OF_YEAR));
        calendar.set(calendar.DAY_OF_YEAR, dayOfYear);
        return calendar.getTime();
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
}
