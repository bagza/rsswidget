package com.example.gryazin.rsswidget.domain;


import java.util.Date;
import java.util.Optional;

/**
 * Created by Zver on 15.11.2016.
 */

public class FeedItem {
    private Optional<Date> date = Optional.empty();
    private String title;
    private String description;
    private String url;

    public boolean hasDate(){
        return date.isPresent();
    }

    public Optional<Date> getDate() {
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
}
