package com.example.gryazin.rsswidget.data.db.cursors;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.gryazin.rsswidget.data.WidgetState;
import com.example.gryazin.rsswidget.data.update.IllegalUpdateException;
import com.example.gryazin.rsswidget.domain.UpdateStatus;
import com.example.gryazin.rsswidget.utils.Utils;

/**
 * Created by Dmitry Gryazin on 20.11.2016.
 */

public class UpdateStatusCursorWrapper extends TypedCursorWrapper<UpdateStatus> {

    public static final String UPDATE_STATUS_TABLE = "updates";

    public static final String WIDGET_ID = "widget_id";
    public static final String TIMESTAMP = "update_timestamp";
    public static final String ERROR = "error_message";

    public UpdateStatusCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    @Override
    public UpdateStatus peek() {
        moveToFirst();
        int widgetId = getInteger(WIDGET_ID, -1);
        if (hasNonNullValue(ERROR)){
            String error = getString(ERROR, "");
            return UpdateStatus.StatusError.ofErrorMessage(widgetId, error);
        }
        else if (hasNonNullValue(TIMESTAMP)){
            Long timestamp = getLong(TIMESTAMP, 0L);
            return  UpdateStatus.StatusSuccess.ofSyncTimestamp(widgetId, timestamp);
        }
        return UpdateStatus.StatusEmpty.ofEmpty(widgetId);
    }

    public static ContentValues toCv(UpdateStatus status){
        ContentValues cv = new ContentValues();
        status.accept(new UpdateStatus.UpdateStatusVisitor<Void>() {
            @Override
            public Void onSuccess(int widgetId, long timestamp) {
                cv.put(WIDGET_ID, widgetId);
                cv.put(TIMESTAMP, timestamp);
                return null;
            }

            @Override
            public Void onEmpty(int widgetId) {
                cv.put(WIDGET_ID, widgetId);
                return null;
            }

            @Override
            public Void onError(int widgetId, String message) {
                cv.put(WIDGET_ID, widgetId);
                cv.put(ERROR, message);
                return null;
            }
        });
        return cv;
    }
}
