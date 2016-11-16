package com.example.gryazin.rsswidget.domain;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class Channel {
    //TODO possibly need to introduce channel GUID
    private String channelUrl;

    public Channel(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }
}
