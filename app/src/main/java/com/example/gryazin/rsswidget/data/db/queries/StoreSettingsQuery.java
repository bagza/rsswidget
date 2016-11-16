package com.example.gryazin.rsswidget.data.db.queries;

import android.database.sqlite.SQLiteDatabase;

import com.example.gryazin.rsswidget.data.db.cursors.FeedCursorWrapper;
import com.example.gryazin.rsswidget.data.db.cursors.SettingsCursorWrapper;
import com.example.gryazin.rsswidget.domain.RssSettings;

import static com.example.gryazin.rsswidget.data.db.cursors.SettingsCursorWrapper.*;


/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class StoreSettingsQuery implements WriteQuery {

    private RssSettings settings;

    public StoreSettingsQuery(RssSettings settings) {
        this.settings = settings;
    }

    @Override
    public void execute(SQLiteDatabase db) throws Exception {
        db.insertWithOnConflict(SETTINGS_TABLE, null, toCv(settings), SQLiteDatabase.CONFLICT_REPLACE);
    }
}
