package com.example.gryazin.rsswidget.domain;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public abstract class UpdateStatus {
    protected long timestamp;
    protected String message;

    public interface UpdateStatusVisitor<T>{
        T onSuccess(long timestamp);
        T onEmpty();
        T onError(String message);
    }

    public abstract <T> T accept(UpdateStatusVisitor<T> visitor);

    public static class StatusSuccess extends UpdateStatus{

        private StatusSuccess(long timestamp) {
            this.timestamp = timestamp;
        }

        public static StatusSuccess ofSyncTimestamp(long timestamp){
            return new StatusSuccess(timestamp);
        }

        @Override
        public <T> T accept(UpdateStatusVisitor<T> visitor) {
            return visitor.onSuccess(timestamp);
        }
    }

    public static class StatusEmpty extends UpdateStatus{

        private StatusEmpty() {
        }

        public static StatusEmpty ofEmpty(){
            return new StatusEmpty();
        }

        @Override
        public <T> T accept(UpdateStatusVisitor<T> visitor) {
            return visitor.onEmpty();
        }
    }

    public static class StatusError extends UpdateStatus{

        private StatusError(String msg) {
            this.message = msg;
        }

        public static StatusError ofErrorMessage(String msg){
            return new StatusError(msg);
        }

        @Override
        public <T> T accept(UpdateStatusVisitor<T> visitor) {
            return visitor.onError(message);
        }
    }




}
