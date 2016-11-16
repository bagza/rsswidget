package com.example.gryazin.rsswidget.data.db.queries;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gryazin.rsswidget.data.db.cursors.FeedCursorWrapper;
import com.example.gryazin.rsswidget.domain.FeedItem;
import com.example.gryazin.rsswidget.utils.Utils;

import java.util.SortedSet;
import java.util.TreeSet;

import static com.example.gryazin.rsswidget.data.db.cursors.FeedCursorWrapper.*;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class ReadChannelFeeds implements ReadQuery<SortedSet<? extends FeedItem>>{

    private String channelUrl;

    public ReadChannelFeeds(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    @Override
    public SortedSet<? extends FeedItem> execute(SQLiteDatabase db) {
        Cursor cursor = db.query(FEEDS_TABLE, null, CHANNEL_URL + "=?", Utils.args(channelUrl), null, null, null);
        FeedCursorWrapper feedCursorWrapper = new FeedCursorWrapper(cursor);
        SortedSet<FeedItem> feedSet = new TreeSet<>();
        feedCursorWrapper.peekCollectionAndClose(feedSet);
        return feedSet;
    }
}
