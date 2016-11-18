package com.example.gryazin.rsswidget.data.db.queries;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gryazin.rsswidget.data.WidgetState;
import com.example.gryazin.rsswidget.data.db.cursors.WidgetStateCursorWrapper;
import com.example.gryazin.rsswidget.utils.Utils;

import static com.example.gryazin.rsswidget.data.db.cursors.SettingsCursorWrapper.SETTINGS_TABLE;
import static com.example.gryazin.rsswidget.data.db.cursors.WidgetStateCursorWrapper.*;

/**
 * Created by Dmitry Gryazin on 18.11.2016.
 */

public class ReadWidgetStateQuery implements ReadQuery<WidgetState> {

    private int widgetId;

    public ReadWidgetStateQuery(int widgetId) {
        this.widgetId = widgetId;
    }

    @Override
    public WidgetState execute(SQLiteDatabase db) {
        Cursor cursor = db.query(VIEW_STATES_TABLE, null, STATE_WIDGET_ID + "=?", Utils.args(widgetId), null, null, null);
        WidgetState widgetState = new WidgetStateCursorWrapper(cursor).peek();
        cursor.close();
        return widgetState;
    }
}
