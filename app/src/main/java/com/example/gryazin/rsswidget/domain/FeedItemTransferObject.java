package com.example.gryazin.rsswidget.domain;

import com.example.gryazin.rsswidget.Utils;

/**
 * Created by Zver on 16.11.2016.
 */

public class FeedItemTransferObject {
    private boolean hasNext;
    private boolean hasPrev;
    private FeedItem feedItem;
    private UpdateStatus updateStatus;

    public boolean hasNext() {
        return hasNext;
    }

    public boolean hasPrev() {
        return hasPrev;
    }

    public FeedItem getFeedItem() {
        return feedItem;
    }

    public UpdateStatus getUpdateStatus() {
        return updateStatus;
    }

    private FeedItemTransferObject(Builder builder){
        this.feedItem = builder.feedItem;
        this.hasNext = builder.hasNext;
        this.hasPrev = builder.hasPrev;
        this.updateStatus = builder.updateStatus;
    }

    public static class Builder{
        //mandatory
        private FeedItem feedItem;
        private UpdateStatus updateStatus;
        //optional
        private boolean hasNext = false;
        private boolean hasPrev = false;

        public Builder hasPrev(boolean prev){
            this.hasPrev = prev;
            return this;
        }

        public Builder hasNext(boolean next){
            this.hasNext = next;
            return this;
        }

        public Builder withFeed(FeedItem feedItem){
            this.feedItem = feedItem;
            return this;
        }

        public Builder withStatus(UpdateStatus updateStatus){
            this.updateStatus = updateStatus;
            return this;
        }

        public FeedItemTransferObject build(){
            Utils.debugAssert(feedItem != null);
            Utils.debugAssert(updateStatus != null);

            return new FeedItemTransferObject(this);
        }
    }
}
