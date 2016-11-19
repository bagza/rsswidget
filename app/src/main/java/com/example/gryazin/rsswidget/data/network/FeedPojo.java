package com.example.gryazin.rsswidget.data.network;

import com.example.gryazin.rsswidget.domain.FeedItem;

/**
 * Created by bag on 19.11.16.
 */

public class FeedPojo {
    private String title;
    private String description;
    private String pubDate;
    private String guid;
    private String url;

    public FeedPojo(String title, String description, String pubDate, String guid, String url) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.guid = guid;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
