package com.example.gryazin.rsswidget.ui;

import android.support.annotation.Nullable;

import com.example.gryazin.rsswidget.domain.FeedItem;
import com.example.gryazin.rsswidget.domain.UpdateStatus;
import com.example.gryazin.rsswidget.utils.Utils;

import java.util.Optional;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class FeedViewModel {
    private boolean hasNext;
    private boolean hasPrev;
    private Optional<Long> updateTimestamp;
    private String title;
    private String body;

    public boolean hasNext() {
        return hasNext;
    }

    public boolean hasPrev() {
        return hasPrev;
    }

    public Optional<Long> maybeGetTimestamp() {
        return updateTimestamp;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    private FeedViewModel(Builder builder){
        this.body = builder.body;
        this.title = builder.title;
        this.hasNext = builder.hasNext;
        this.hasPrev = builder.hasPrev;
        this.updateTimestamp = Optional.ofNullable(builder.timestamp);
    }

    public static class Builder{
        //mandatory
        private String title;
        private String body;
        //optional
        private boolean hasNext = false;
        private boolean hasPrev = false;
        private Long timestamp = null;

        public static Builder builder(){
            return new Builder();
        }

        public Builder byFeedItem(FeedItem feedItem){
            return this.withTitle(feedItem.getTitle())
                       .withBody(feedItem.getDescription());
        }

        public Builder withPrev(boolean prev){
            this.hasPrev = prev;
            return this;
        }

        public Builder withNext(boolean next){
            this.hasNext = next;
            return this;
        }

        public Builder withTitle(String title){
            this.title = title;
            return this;
        }

        public Builder withBody(String body){
            this.body = body;
            return this;
        }

        public Builder withTimestamp(long timestamp){
            this.timestamp = timestamp;
            return this;
        }

        public FeedViewModel build(){
            Utils.debugAssert(title != null && body != null);

            return new FeedViewModel(this);
        }
    }
}
