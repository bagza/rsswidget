package com.example.gryazin.rsswidget.data.db.cursors;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.gryazin.rsswidget.domain.Channel;
import com.example.gryazin.rsswidget.domain.FeedItem;

import java.util.Date;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class FeedCursorWrapper extends TypedCursorWrapper<FeedItem> {

    public static final String FEEDS_TABLE = "feeds";

    public static final String FEED_GUID = "feed_guid";
    public static final String FEED_TITLE = "feed_title";
    public static final String FEED_DESCRIPTION = "feed_description";
    public static final String FEED_URL = "feed_url";
    public static final String FEED_PUBDATE = "feed_pubdate";
    public static final String CHANNEL_URL = "channel_url";

    public FeedCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    @Override
    public FeedItem peek() {
        FeedItem feedItem = new FeedItem();
        feedItem.setTitle(getString(FEED_TITLE, ""));
        feedItem.setUrl(getString(FEED_URL, ""));
        feedItem.setDescription(getString(FEED_DESCRIPTION, ""));
        feedItem.setGuid(getString(FEED_GUID, ""));
        feedItem.setChannel(new Channel(getString(CHANNEL_URL, "")));
        if (!isNull(getColumnIndex(FEED_PUBDATE))){
            feedItem.setDate(new Date(getLong(FEED_PUBDATE, 0)));
        }
        return null;
    }

    public static ContentValues toCv(FeedItem feedItem){
        ContentValues cv = new ContentValues();
        cv.put(FEED_GUID, feedItem.getGuid());
        cv.put(FEED_TITLE, feedItem.getTitle());
        cv.put(FEED_DESCRIPTION, feedItem.getDescription());
        cv.put(FEED_URL, feedItem.getUrl());
        cv.put(CHANNEL_URL, feedItem.getChannelUrl());
        if (feedItem.hasDate()) {
            cv.put(FEED_PUBDATE, feedItem.maybeGetDate().get().getTime());
        }
        return cv;
    }


}
