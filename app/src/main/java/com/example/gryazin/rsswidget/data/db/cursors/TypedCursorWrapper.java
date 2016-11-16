package com.example.gryazin.rsswidget.data.db.cursors;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Collection;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public abstract class TypedCursorWrapper<T> extends CursorWrapper {

    public TypedCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public abstract T peek();

    public void peekCollectionAndClose(Collection<? super T> collection){
        moveToFirst();
        while(moveToNext()) {
            collection.add(peek());
        }
        close();
    };

    public boolean isEmpty() {
        return getCount() == 0;
    }

    public String getString(String columnName, String defaultValue) {
        int index = getColumnIndex(columnName);
        if (isValidIndex(index)) {
            return getString(index);
        } else {
            return defaultValue;
        }
    }

    public long getLong(String columnName, long defaultValue) {
        int index = getColumnIndex(columnName);
        if (isValidIndex(index)) {
            return getLong(index);
        } else {
            return defaultValue;
        }
    }

    public int getInteger(String columnName, int defaultValue) {
        int index = getColumnIndex(columnName);
        if (isValidIndex(index)) {
            return getInt(index);
        } else {
            return defaultValue;
        }
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < getColumnCount();
    }
}
