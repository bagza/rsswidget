package com.example.gryazin.rsswidget.data.db.queries;

import android.database.sqlite.SQLiteDatabase;

import com.example.gryazin.rsswidget.data.WidgetState;

import static com.example.gryazin.rsswidget.data.db.cursors.WidgetStateCursorWrapper.*;

/**
 * Created by Dmitry Gryazin on 18.11.2016.
 */

public class StoreWidgetStateQuery implements WriteQuery {

    private WidgetState widgetState;

    public StoreWidgetStateQuery(WidgetState widgetState) {
        this.widgetState = widgetState;
    }

    @Override
    public void execute(SQLiteDatabase db) throws Exception {
        db.insertWithOnConflict(VIEW_STATES_TABLE, null, toCv(widgetState), SQLiteDatabase.CONFLICT_REPLACE);
    }
}
