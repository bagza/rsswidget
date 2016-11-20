package com.example.gryazin.rsswidget.domain;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public abstract class UpdateStatus {
    protected int widgetId;
    protected long timestamp;
    protected String message;

    public interface UpdateStatusVisitor<T>{
        T onSuccess(int widgetId, long timestamp);
        T onEmpty(int widgetId);
        T onError(int widgetId, String message);
    }

    public abstract <T> T accept(UpdateStatusVisitor<T> visitor);

    public static class StatusSuccess extends UpdateStatus{

        private StatusSuccess(int widgetId, long timestamp) {
            this.timestamp = timestamp;
            this.widgetId = widgetId;
        }

        public static StatusSuccess ofSyncTimestamp(int widgetId, long timestamp){
            return new StatusSuccess(widgetId, timestamp);
        }

        @Override
        public <T> T accept(UpdateStatusVisitor<T> visitor) {
            return visitor.onSuccess(widgetId, timestamp);
        }
    }

    public static class StatusEmpty extends UpdateStatus{

        private StatusEmpty(int widgetId) {
            this.widgetId = widgetId;
        }

        public static StatusEmpty ofEmpty(int widgetId){
            return new StatusEmpty(widgetId);
        }

        @Override
        public <T> T accept(UpdateStatusVisitor<T> visitor) {
            return visitor.onEmpty(widgetId);
        }
    }

    public static class StatusError extends UpdateStatus{

        private StatusError(int widgetId, String msg) {
            this.message = msg;
            this.widgetId = widgetId;
        }

        public static StatusError ofErrorMessage(int widgetId, String msg){
            return new StatusError(widgetId, msg);
        }

        @Override
        public <T> T accept(UpdateStatusVisitor<T> visitor) {
            return visitor.onError(widgetId, message);
        }
    }




}
