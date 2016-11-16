package com.example.gryazin.rsswidget.data.db.cursors;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.gryazin.rsswidget.data.ViewState;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class ViewStateCursorWrapper extends TypedCursorWrapper<ViewState> {
    public static final String VIEW_STATES_TABLE = "view_states";

    public static final String STATE_WIDGET_ID = "widget_id";
    public static final String STATE_LAST_GUID = "last_guid";
    public static final String STATE_LAST_POSITION = "last_position";

    public ViewStateCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    @Override
    public ViewState peek() {
        ViewState viewState = new ViewState();
        viewState.setAppWidgetId(getInteger(STATE_WIDGET_ID, 0));
        viewState.setLastWatchedFeedGuid(getString(STATE_LAST_GUID, ""));
        viewState.setLastWatchedPosition(getInteger(STATE_LAST_POSITION, 0));
        return viewState;
    }

    public static ContentValues toCv(ViewState state){
        ContentValues cv = new ContentValues();
        cv.put(STATE_WIDGET_ID, state.getAppWidgetId());
        cv.put(STATE_LAST_GUID, state.getLastWatchedFeedGuid());
        cv.put(STATE_LAST_POSITION, state.getLastWatchedPosition());
        return cv;
    }
}
