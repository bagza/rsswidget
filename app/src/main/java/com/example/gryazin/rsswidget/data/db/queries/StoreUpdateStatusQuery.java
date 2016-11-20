package com.example.gryazin.rsswidget.data.db.queries;

import android.database.sqlite.SQLiteDatabase;

import com.example.gryazin.rsswidget.data.db.cursors.UpdateStatusCursorWrapper;
import com.example.gryazin.rsswidget.domain.UpdateStatus;

import static com.example.gryazin.rsswidget.data.db.cursors.UpdateStatusCursorWrapper.*;

/**
 * Created by Dmitry Gryazin on 20.11.2016.
 */

public class StoreUpdateStatusQuery implements WriteQuery{
    private UpdateStatus status;

    public StoreUpdateStatusQuery(UpdateStatus status) {
        this.status = status;
    }

    @Override
    public void execute(SQLiteDatabase db) throws Exception {
        db.insertWithOnConflict(UPDATE_STATUS_TABLE, null, toCv(status), SQLiteDatabase.CONFLICT_REPLACE);
    }
}
