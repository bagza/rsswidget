package com.example.gryazin.rsswidget.domain;

/**
 * Created by Zver on 16.11.2016.
 */

public class RssSettings {
    private static long ONE_MINUTE_MS = 1 * 60 * 1000L;
    public static Long pollPeriodMs = ONE_MINUTE_MS;

    private int appWidgetId;
    private String rssUrl;

    public RssSettings(int appWidgetId, String rssUrl) {
        this.appWidgetId = appWidgetId;
        this.rssUrl = rssUrl;
    }

    public int getAppWidgetId() {
        return appWidgetId;
    }

    public void setAppWidgetId(int appWidgetId) {
        this.appWidgetId = appWidgetId;
    }

    public String getRssUrl() {
        return rssUrl;
    }

    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }
}
