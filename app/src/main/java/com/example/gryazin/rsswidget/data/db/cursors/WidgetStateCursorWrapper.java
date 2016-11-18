package com.example.gryazin.rsswidget.data.db.cursors;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.gryazin.rsswidget.data.WidgetState;

/**
 * Created by Dmitry Gryazin on 16.11.2016.
 */

public class WidgetStateCursorWrapper extends TypedCursorWrapper<WidgetState> {
    public static final String VIEW_STATES_TABLE = "view_states";

    public static final String STATE_WIDGET_ID = "widget_id";
    public static final String STATE_LAST_GUID = "last_guid";
    public static final String STATE_LAST_POSITION = "last_position";

    public WidgetStateCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    @Override
    public WidgetState peek() {
        if (getCount() < 1){
            return new WidgetState.StateEmpty();
        }
        moveToFirst();
        if (getString(STATE_LAST_GUID, "").isEmpty()){
            return new WidgetState.StateEmpty();
        }

        int pos = getInteger(STATE_LAST_POSITION, 0);
        int id = getInteger(STATE_WIDGET_ID, 0);
        String guid = getString(STATE_LAST_GUID, "");
        WidgetState widgetState = new WidgetState.StateLastWatched(id, pos, guid);
        widgetState.setAppWidgetId(getInteger(STATE_WIDGET_ID, 0));
        return widgetState;
    }

    public static ContentValues toCv(WidgetState state){
        ContentValues cv = new ContentValues();
        cv.put(STATE_WIDGET_ID, state.getAppWidgetId());
        cv.put(STATE_LAST_GUID, state.getLastWatchedFeedGuid());
        cv.put(STATE_LAST_POSITION, state.getLastWatchedPosition());
        return cv;
    }
}
