package com.example.gryazin.rsswidget.data.db.cursors;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.gryazin.rsswidget.domain.Channel;
import com.example.gryazin.rsswidget.domain.RssSettings;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class ChannelCursorWrapper extends TypedCursorWrapper<Channel> {

    public static final String CHANNELS_TABLE = "channels";

    public static final String CHANNEL_URL = "channel_url";

    public ChannelCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    @Override
    public Channel peek() {
        return new Channel(getString(CHANNEL_URL, ""));
    }

    public static ContentValues toCv(Channel channel){
        ContentValues cv = new ContentValues();
        cv.put(CHANNEL_URL, channel.getChannelUrl());
        return cv;
    }
}
