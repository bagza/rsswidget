package com.example.gryazin.rsswidget.data;

import android.os.SystemClock;

import com.example.gryazin.rsswidget.data.db.RssDatabase;
import com.example.gryazin.rsswidget.domain.Channel;
import com.example.gryazin.rsswidget.utils.DateUtils;
import com.example.gryazin.rsswidget.utils.Utils;
import com.example.gryazin.rsswidget.domain.FeedItem;
import com.example.gryazin.rsswidget.domain.RssSettings;
import com.example.gryazin.rsswidget.domain.UpdateStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class FakeRepository extends LocalRepository{

    private SortedSet<FeedItem> feedItems1 = new TreeSet<>();
    private SortedSet<FeedItem> feedItems2 = new TreeSet<>();
    private static final String LONG_DESCRIPTION = " Lorem Ipsum is simply dummy text of the printing and typesetting industry.";

    public FakeRepository(RssDatabase database, Preferences preferences) {
        super(database, preferences);
        bakeFakes();
        storeFeeds(feedItems1);
        storeFeeds(feedItems2);
        saveTimestamp(System.currentTimeMillis());
    }

    private void bakeFakes(){
        for (int i = 0; i < 3; i++){
            feedItems1.add(generateRandomFeed());
            feedItems2.add(generateRandomFeed());
        }
    }

    private FeedItem generateRandomFeed(){
        Date randomDate = DateUtils.randomDate();
        FeedItem feedItem = new FeedItem();
        feedItem.setDate(randomDate);
        feedItem.setGuid(UUID.randomUUID().toString());
        feedItem.setTitle("Title" + DateUtils.randBetween(0, 10));
        feedItem.setDescription(randomDate.toString() + LONG_DESCRIPTION);
        feedItem.setUrl("url");
        feedItem.setChannel(new Channel("https://stackoverflow.com/jobs/feed"));
        return feedItem;
    }
}
