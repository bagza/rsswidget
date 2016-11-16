package com.example.gryazin.rsswidget.data;

import com.example.gryazin.rsswidget.domain.FeedItem;
import com.example.gryazin.rsswidget.domain.RssSettings;

import java.util.Collection;
import java.util.SortedSet;

/**
 * Created by Zver on 16.11.2016.
 */

public class FakeRepository implements Repository {
    @Override
    public SortedSet<? extends FeedItem> getAllFeedsByWidgetId(int widgetId) {
        return null;
    }

    @Override
    public Collection<? extends RssSettings> getAllSettings() {
        return null;
    }

    @Override
    public Long getUpdateTimestamp() {
        return null;
    }
}
