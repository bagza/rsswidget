package com.example.gryazin.rsswidget.data;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public abstract class WidgetState {
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

    public abstract <T> T accept(Visitor<T> visitor);

    public interface Visitor<T>{
        T onLastWatched(int lastWatchedPosition, String lastWatchedFeedGuid);
        T onEmpty();
    }

    public static class StateEmpty extends WidgetState{
        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.onEmpty();
        }
    }

    public static class StateLastWatched extends WidgetState{

        public StateLastWatched(int lastWatchedPosition, String lastWatchedFeedGuid) {
            setLastWatchedFeedGuid(lastWatchedFeedGuid);
            setLastWatchedPosition(lastWatchedPosition);
        }

        @Override
        public <T> T accept(Visitor<T> visitor) {
            return visitor.onEmpty();
        }
    }


}
