package com.example.gryazin.rsswidget.data.db.queries;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gryazin.rsswidget.data.db.cursors.SettingsCursorWrapper;
import com.example.gryazin.rsswidget.domain.RssSettings;
import com.example.gryazin.rsswidget.utils.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.example.gryazin.rsswidget.data.db.cursors.SettingsCursorWrapper.*;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class ReadSettingsQuery implements ReadQuery<Collection<? extends RssSettings>>{

    @Override
    public Collection<? extends RssSettings> execute(SQLiteDatabase db) {
        Cursor cursor = db.query(SETTINGS_TABLE, null, null, null, null, null, null);
        List<RssSettings> settings = new ArrayList<>();
        SettingsCursorWrapper settingsCursorWrapper = new SettingsCursorWrapper(cursor);
        settingsCursorWrapper.peekCollectionAndClose(settings);
        return settings;
    }
}
