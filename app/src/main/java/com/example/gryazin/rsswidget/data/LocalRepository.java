package com.example.gryazin.rsswidget.data;

import com.example.gryazin.rsswidget.data.db.RssDatabase;
import com.example.gryazin.rsswidget.data.update.IllegalUpdateException;
import com.example.gryazin.rsswidget.domain.FeedItem;
import com.example.gryazin.rsswidget.domain.RssSettings;
import com.example.gryazin.rsswidget.domain.UpdateStatus;

import java.util.Collection;
import java.util.TreeSet;

import javax.inject.Inject;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 * WISDOM: all calls are synchronous, because all job is done in background by services.
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
    public TreeSet<? extends FeedItem> getAllFeedsByWidgetId(int widgetId){
        String channelUrl = getChannelUrlForWidget(widgetId);
        return database.readFeedItemsByChannel(channelUrl);
    }

    @Override
    public Collection<? extends RssSettings> getAllSettings() {
        return database.getAllSettings();
    }

    @Override
    public void saveSettings(RssSettings settings) {
        database.storeSettings(settings);
    }

    @Override
    public void deleteSettings(int widgetId) {
        database.deleteSettings(widgetId);
    }

    private String getChannelUrlForWidget(int widgetId){
        Collection<? extends RssSettings> allSettings = getAllSettings();
        RssSettings mSetting = allSettings.stream()
                .filter(setting -> setting.getAppWidgetId() == widgetId)
                .findAny()
                .orElse(null);
                //Guess it's JDK bug! It says throwable not handled, while it's RuntimeException!!
                //There is a similar issue in oracle tracker already.
                //.orElseThrow(() -> new IllegalArgumentException("No such widgetid in settings"));
        if (mSetting == null){
            throw new IllegalUpdateException(widgetId);
        }
        return mSetting.getRssUrl();
    }

    @Override
    public WidgetState getWidgetStateById(int widgetId) {
        return database.getWidgetStateById(widgetId);
    }

    @Override
    public void saveWidgetState(WidgetState widgetState) {
        database.storeWidgetState(widgetState);
    }

    public void saveUpdateStatus(UpdateStatus status){
        database.storeUpdateStatus(status);
    }

    public UpdateStatus getUpdateStatusForWidget(int widgetId){
        return database.getUpdateStatusForWidget(widgetId);
    }
}
