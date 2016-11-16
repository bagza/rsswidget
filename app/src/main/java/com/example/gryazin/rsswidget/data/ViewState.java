package com.example.gryazin.rsswidget.data;

/**
 * Created by Zver on 16.11.2016.
 */

public class ViewState {
    private int appWidgetId;
    private int lastWatchedPosition;
    private String lastWatchedFeedGuid;

    public int getAppWidgetId() {
        return appWidgetId;
    }

    public void setAppWidgetId(int appWidgetId) {
        this.appWidgetId = appWidgetId;
    }

    public int getLastWatchedPosition() {
        return lastWatchedPosition;
    }

    public void setLastWatchedPosition(int lastWatchedPosition) {
        this.lastWatchedPosition = lastWatchedPosition;
    }

    public String getLastWatchedFeedGuid() {
        return lastWatchedFeedGuid;
    }

    public void setLastWatchedFeedGuid(String lastWatchedFeedGuid) {
        this.lastWatchedFeedGuid = lastWatchedFeedGuid;
    }
}
