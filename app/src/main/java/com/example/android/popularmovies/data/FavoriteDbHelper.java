package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class FavoriteDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favorite_movie.db";

    private static final int DATABASE_VERSION = 1;

    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        /*
         * This String will contain a simple SQL statement that will create a table that will
         * cache our weather data.
         */
        final String SQL_CREATE_WEATHER_TABLE =

                "CREATE TABLE " + FavoriteContract.FavoriteEntry.TABLE_NAME + " (" +

                        FavoriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                        FavoriteContract.FavoriteEntry.COLUMN_ID_MOVIE + " INTEGER NOT NULL, " +

                        FavoriteContract.FavoriteEntry.COLUMN_URL_IMAGE + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_WEATHER_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

