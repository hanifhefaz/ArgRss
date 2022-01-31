package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseHandler {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;


    public DatabaseHandler(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //methods for all table
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void loadImage(String news) {
        database.delete( "news", null, null);
    }

    //news table method

    public void insertNewsInfo(NewsInformation newsInfo) {
        ContentValues cv = new ContentValues();
        cv.put("link"          ,  newsInfo.link );
        cv.put("title"           ,  newsInfo.title );
        cv.put("pubDate"        ,  newsInfo.pubDate );
        cv.put("description"        ,  newsInfo.description );
        cv.put("image"        ,  newsInfo.image );


        database.insert("news" , "writerName", cv);
    }

//    public void updateNewsInfo(NewsInformation newsInfo) {
//        ContentValues cv = new ContentValues();
//        cv.put("link"          ,  newsInfo.link );
//        cv.put("title"           ,  newsInfo.title );
//        cv.put("pubDate"        ,  newsInfo.pubDate );
//        cv.put("description"        ,  newsInfo.description );
//        cv.put("image"        ,  newsInfo.image );
//
//
//        database.update("news",cv,"link=?", new String[]{newsInfo.link});
//    }


    public ArrayList<NewsInformation> getAllNews() {
        ArrayList<NewsInformation> NewsInfoList = new ArrayList<NewsInformation>();

        Cursor cursor = database.rawQuery("SELECT DISTINCT title, description, pubDate FROM  news ORDER BY pubDate DESC" ,new String[]{});

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            NewsInformation newsInfo = new NewsInformation();

//            newsInfo.link = cursor.getString(0);
            newsInfo.title = cursor.getString(0);
            newsInfo.description = cursor.getString(1);
            newsInfo.pubDate = cursor.getString(2);
//            newsInfo.image = cursor.getString(4);

            NewsInfoList.add(newsInfo);
            cursor.moveToNext();
        }

        // Make sure to close the cursor
        cursor.close();

        return NewsInfoList;
    }

}
