package com.example.gryazin.rsswidget.domain;


import java.util.Date;
import java.util.Optional;

/**
 * Created by Dmitry Gryazin on 15.11.2016.
 */

public class FeedItem implements Comparable<FeedItem>{
    private String guid;
    private Optional<Date> date = Optional.empty();
    private String title;
    private String description;
    private String url;
    private Channel channel;

    public boolean hasDate(){
        return date.isPresent();
    }

    public Optional<Date> maybeGetDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = Optional.of(date);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getChannelUrl(){
        return channel.getChannelUrl();
    }

    @Override
    public int compareTo(FeedItem o) {
        if (hasDate() && o.hasDate()){
            return compareByDates(o);
        }
        else{
            return compareByTitles(o);
        }
    }

    private int compareByDates(FeedItem o){
        return -1 * date.get().compareTo(o.maybeGetDate().get());
    }

    private int compareByTitles(FeedItem o){
        return title.compareTo(o.getTitle());
    }
}
