package com.example.gryazin.rsswidget.data.network;

import com.example.gryazin.rsswidget.domain.Channel;
import com.example.gryazin.rsswidget.domain.FeedItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by bag on 19.11.16.
 */

public class FeedPojoMapper {

    private static DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

    public static FeedItem map(FeedPojo feedPojo, String url){
        FeedItem feedItem = new FeedItem();
        feedItem.setChannel(new Channel(url));
        feedItem.setGuid(feedPojo.getGuid());
        try {
            feedItem.setDate(formatter.parse(feedPojo.getPubDate()));
        } catch (ParseException e) {
            //no date, ok, it's optional
        }
        feedItem.setUrl(feedPojo.getUrl());
        feedItem.setTitle(feedPojo.getTitle());
        feedItem.setDescription(feedPojo.getDescription());

        return feedItem;
    }

    public static List<FeedItem> mapAll(Collection<FeedPojo> pojos, String channelUrl){
        List<FeedItem> feeds = new ArrayList<>(pojos.size());
        for (FeedPojo pojo : pojos){
            feeds.add(map(pojo, channelUrl));
        }
        return feeds;
    }
}
