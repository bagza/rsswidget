package com.example.gryazin.rsswidget.domain;

/**
 * Created by Zver on 16.11.2016.
 */

public abstract class UpdateStatus {

    protected long timestamp;
    protected String message;

    interface UpdateStatusVisitor<T>{
        T onSuccess(long timestamp);
        T onPending(long timestamp);
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

    public static class StatusPending extends UpdateStatus{

        private StatusPending(long timestamp) {
            this.timestamp = timestamp;
        }

        public static StatusPending ofLastSyncTimestamp(long timestamp){
            return new StatusPending(timestamp);
        }

        @Override
        public <T> T accept(UpdateStatusVisitor<T> visitor) {
            return visitor.onPending(timestamp);
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
