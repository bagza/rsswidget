package com.example.gryazin.rsswidget.data.db.queries;

import android.database.sqlite.SQLiteDatabase;

import com.example.gryazin.rsswidget.data.db.cursors.FeedCursorWrapper;
import com.example.gryazin.rsswidget.domain.FeedItem;

import java.util.Collection;

import static com.example.gryazin.rsswidget.data.db.cursors.FeedCursorWrapper.FEEDS_TABLE;

/**
 * Created by Zver on 16.11.2016.
 */

public class StoreFeedsQuery implements WriteQuery {

    private Collection<? extends FeedItem> feedItems;

    public StoreFeedsQuery(Collection<? extends FeedItem> feedItems) {
        this.feedItems = feedItems;
    }

    @Override
    public void execute(SQLiteDatabase db) throws Exception {
        for (FeedItem feedItem : feedItems) {
            db.insertWithOnConflict(FEEDS_TABLE, null, FeedCursorWrapper.toCv(feedItem), SQLiteDatabase.CONFLICT_REPLACE);
        }
    }
}
