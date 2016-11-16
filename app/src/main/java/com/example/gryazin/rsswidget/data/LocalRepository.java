package com.example.gryazin.rsswidget.data;

import com.example.gryazin.rsswidget.data.db.RssDatabase;
import com.example.gryazin.rsswidget.domain.FeedItem;
import com.example.gryazin.rsswidget.domain.RssSettings;
import com.example.gryazin.rsswidget.domain.UpdateStatus;

import java.util.Collection;
import java.util.SortedSet;

import javax.inject.Inject;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class LocalRepository implements Repository {

    private RssDatabase database;
    private Preferences preferences;

    @Inject
    public LocalRepository(RssDatabase database, Preferences preferences) {
        this.database = database;
        this.preferences = preferences;
    }

    @Override
    public void storeFeeds(Collection<? extends FeedItem> feedItems) {
        database.storeFeeds(feedItems);
    }

    @Override
    public SortedSet<? extends FeedItem> getAllFeedsByWidgetId(int widgetId) throws Throwable {
        String channelUrl = getChannelUrlForWidget(widgetId);
        return database.readFeedItemsByChannel(channelUrl);
    }

    @Override
    public Collection<? extends RssSettings> getAllSettings() {
        return database.getAllSettings();
    }

    @Override
    public UpdateStatus getUpdateStatus() {
        if (!preferences.hasTimestamp()){
            return UpdateStatus.StatusEmpty.ofEmpty();
        }
        //TODO make error here
        else{
            long timestamp = preferences.getUpdateTimestamp();
            return UpdateStatus.StatusSuccess.ofSyncTimestamp(timestamp);
        }
    }

    @Override
    public void saveSettings(RssSettings settings) {
        database.storeSettings(settings);
    }

    private String getChannelUrlForWidget(int widgetId) throws Throwable {
        RssSettings mSetting = getAllSettings().stream()
                .filter(setting -> setting.getAppWidgetId() == widgetId)
                .findAny()
                //.orElse(null);
                .orElseThrow(() -> new IllegalArgumentException("No such widgetid in settings"));
        return mSetting.getRssUrl();
    }
}
