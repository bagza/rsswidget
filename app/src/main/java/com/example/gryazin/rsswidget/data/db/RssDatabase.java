package com.example.gryazin.rsswidget.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.gryazin.rsswidget.data.WidgetState;
import com.example.gryazin.rsswidget.data.db.queries.ReadChannelFeeds;
import com.example.gryazin.rsswidget.data.db.queries.ReadQuery;
import com.example.gryazin.rsswidget.data.db.queries.ReadSettingsQuery;
import com.example.gryazin.rsswidget.data.db.queries.ReadWidgetStateQuery;
import com.example.gryazin.rsswidget.data.db.queries.StoreFeedsQuery;
import com.example.gryazin.rsswidget.data.db.queries.StoreSettingsQuery;
import com.example.gryazin.rsswidget.data.db.queries.StoreWidgetStateQuery;
import com.example.gryazin.rsswidget.data.db.queries.WriteQuery;
import com.example.gryazin.rsswidget.domain.FeedItem;
import com.example.gryazin.rsswidget.domain.RssSettings;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import javax.inject.Inject;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class RssDatabase {
    private Context context;
    private DBHelper dbHelper;

    @Inject
    public RssDatabase(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    private synchronized void writeDatabase(WriteQuery query) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            query.execute(db);
            db.setTransactionSuccessful();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
    }

    private synchronized <T> T readDatabase(ReadQuery<T> query) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            return query.execute(db);
        }
        catch (RuntimeException e) {
            throw e;
        }
        finally {
            db.close();
        }
    }

    public void storeFeeds(Collection<? extends FeedItem> feedItems){
        writeDatabase(new StoreFeedsQuery(feedItems));
    }

    public SortedSet<? extends FeedItem> readFeedItemsByChannel(String channelUrl){
        return readDatabase(new ReadChannelFeeds(channelUrl));
    }

    public void storeSettings(RssSettings settings){
        writeDatabase(new StoreSettingsQuery(settings));
    }

    public Collection<? extends RssSettings> getAllSettings(){
        return readDatabase(new ReadSettingsQuery());
    }

    public void storeWidgetState(WidgetState widgetState){
        writeDatabase(new StoreWidgetStateQuery(widgetState));
    }

    public WidgetState getWidgetStateById(int widgetId){
        return readDatabase(new ReadWidgetStateQuery(widgetId));
    }
}
