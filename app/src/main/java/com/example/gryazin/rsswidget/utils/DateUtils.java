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

    public static Date randomDate(){
        Calendar calendar = GregorianCalendar.getInstance();
        int year = randBetween(2000, 2010);
        calendar.set(calendar.YEAR, year);
        int dayOfYear = randBetween(1, calendar.getActualMaximum(calendar.DAY_OF_YEAR));
        calendar.set(calendar.DAY_OF_YEAR, dayOfYear);
        return calendar.getTime();
    }

    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }
    /*@Nullable
    public static Date dateTimeFromApiString(String stringDate){
        if (stringDate == null || stringDate.equals("")) return null;
        Date date;
        //I don't know what server returns!
        //"2014-03-20T15:29:48+04:00"
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        //"2014-03-20T15:29:48"
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            date = sdf1.parse(stringDate);
        } catch (ParseException e) {
            try {
                date = sdf2.parse(stringDate);
            } catch (ParseException e1) {
                e.printStackTrace();
                e1.printStackTrace();
                return null;
            }
        }
        return date;
    }

    @Nullable
    public static Date dateFromApiString(String stringDate){
        //"1982-03-23"
        if (stringDate == null || stringDate.equals("")) return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String dateToApiString(Date date){
        //"1982-03-23"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }*/
}
