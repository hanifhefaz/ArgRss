package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "db_for_news";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

        //fdd
        Log.i(TAG, "Object created.");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE news ( " +
                " title TEXT , pubDate TEXT , description TEXT ,image TEXT ," +
                " link TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DatabaseHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("Drop table if exists news" );
        onCreate(db);
    }
}
