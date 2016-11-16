package com.example.gryazin.rsswidget.data;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public interface ViewStateKeeper {
    ViewState getViewStateForWidget(int widgetId);
}
