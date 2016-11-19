package com.example.gryazin.rsswidget.data.network;

/**
 * Created by bag on 19.11.16.
 */

public class NetworkFetchException extends RuntimeException {

    public NetworkFetchException() {
    }

    public NetworkFetchException(String message) {
        super(message);
    }

    public NetworkFetchException(String message, Throwable cause) {
        super(message, cause);
    }
}
