package com.example.gryazin.rsswidget.data.db.queries;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Zver on 16.11.2016.
 */
public interface WriteQuery {
    public void execute(SQLiteDatabase db) throws Exception;
}
