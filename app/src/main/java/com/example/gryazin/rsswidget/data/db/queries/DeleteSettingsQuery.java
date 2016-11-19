package com.example.gryazin.rsswidget.data.db.queries;

import android.database.sqlite.SQLiteDatabase;

import com.example.gryazin.rsswidget.data.db.cursors.SettingsCursorWrapper;
import com.example.gryazin.rsswidget.utils.Utils;

import static com.example.gryazin.rsswidget.data.db.cursors.SettingsCursorWrapper.*;

/**
 * Created by Dmitry Gryazin on 19.11.2016.
 */

public class DeleteSettingsQuery implements WriteQuery{
    private int widgetId;

    public DeleteSettingsQuery(int widgetId) {
        this.widgetId = widgetId;
    }

    @Override
    public void execute(SQLiteDatabase db) throws Exception {
        db.delete(SETTINGS_TABLE, SETTINGS_WIDGET_ID + "=?", Utils.args(widgetId));
    }
}
