package com.example.gryazin.rsswidget.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gryazin.rsswidget.utils.Utils;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

/**
 * Created by Zver on 16.11.2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "rss_database";
    private static final int VERSION = 1;
    private Context context;

    @Inject
    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            executeSqlStatements(db, context.getAssets().open("create.sql"));
            onUpgrade(db, 1, VERSION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    public static int getVersion(){
        return VERSION;
    }

    private void executeSqlStatements(SQLiteDatabase db, InputStream supplier) {
        String statements = null;
        try {
            statements = Utils.readFileAsString(supplier);
            for (String stmt : statements.split("(?m)^\\s*$")) {
                db.execSQL(stmt);
            }
       /* try {
            String statements = CharStreams.toString(
                    CharStreams.newReaderSupplier(supplier, Charsets.UTF_8));
            Log.d("SearchDBHelper", "Statements length: " + statements.length() + " " + statements);
            //This check is due to a mistake during one of the upgrade, so update8.sql is empty now
            if(statements.length() >0 ) {
                for (String stmt : statements.split("(?m)^\\s*$")) {
                    db.execSQL(stmt);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create database", e);
        }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

