package com.example.gryazin.rsswidget.data.db.queries;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */
public interface ReadQuery<T> {
    public T execute(SQLiteDatabase db);
}