package com.example.gryazin.rsswidget.data.db.cursors;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.gryazin.rsswidget.domain.RssSettings;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class SettingsCursorWrapper extends TypedCursorWrapper<RssSettings> {

    public static final String SETTINGS_TABLE = "settings";

    public static final String SETTINGS_WIDGET_ID = "widget_id";
    public static final String SETTINGS_CHANNEL_URL = "channel_url";

    public SettingsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    @Override
    public RssSettings peek() {
        int widgetId = getInteger(SETTINGS_WIDGET_ID, 0);
        String channelUrl = getString(SETTINGS_CHANNEL_URL, "");
        return new RssSettings(widgetId, channelUrl);
    }

    public static ContentValues toCv(RssSettings settings){
        ContentValues cv = new ContentValues();
        cv.put(SETTINGS_WIDGET_ID, settings.getAppWidgetId());
        cv.put(SETTINGS_CHANNEL_URL, settings.getRssUrl());
        return cv;
    }
}
