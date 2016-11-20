package com.example.gryazin.rsswidget.data.db.queries;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gryazin.rsswidget.data.db.cursors.UpdateStatusCursorWrapper;
import com.example.gryazin.rsswidget.data.update.IllegalUpdateException;
import com.example.gryazin.rsswidget.domain.UpdateStatus;
import com.example.gryazin.rsswidget.utils.Utils;

import static com.example.gryazin.rsswidget.data.db.cursors.UpdateStatusCursorWrapper.*;

/**
 * Created by Dmitry Gryazin on 20.11.2016.
 */

public class ReadUpdateStatusQuery implements ReadQuery<UpdateStatus>{
    int widgetId;

    public ReadUpdateStatusQuery(int widgetId) {
        this.widgetId = widgetId;
    }

    @Override
    public UpdateStatus execute(SQLiteDatabase db) {
        Cursor cursor = db.query(UPDATE_STATUS_TABLE, null, WIDGET_ID + "=?", Utils.args(widgetId), null, null, null);
        try {
            if (cursor.getCount() < 1) {
                return UpdateStatus.StatusEmpty.ofEmpty(widgetId);
                //throw new IllegalUpdateException(widgetId);
            }
            UpdateStatus updateStatus = new UpdateStatusCursorWrapper(cursor).peek();
            return updateStatus;
        }
        finally {
            cursor.close();
        }

    }
}
