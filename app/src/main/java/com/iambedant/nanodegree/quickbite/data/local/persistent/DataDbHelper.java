package com.iambedant.nanodegree.quickbite.data.local.persistent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kuliza-193 on 4/16/2016.
 */
public class DataDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "quickbite.db";

    public DataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_CUISINE_TABLE = "CREATE TABLE " + DataContract.CuisinesEntry.TABLE_NAME + " (" +
                DataContract.CuisinesEntry._ID + " INTEGER PRIMARY KEY," +
                DataContract.CuisinesEntry.COLUMN_CUISINE_NAME + " TEXT UNIQUE NOT NULL, " +
                DataContract.CuisinesEntry.COLUMN_CUISINE_ID + " TEXT NOT NULL, " +
                DataContract.CuisinesEntry.COLUMN_IS_FAVOURITE + "  INTEGER NOT NULL, " +
                " );";

        final String SQL_CREATE_RESTAURANT_TABLE = "CREATE TABLE " + DataContract.RestaurantEntry.TABLE_NAME + " (" +
                DataContract.RestaurantEntry._ID + " INTEGER PRIMARY KEY," +
                DataContract.RestaurantEntry.COLUMN_RESTAURANT_ID + " TEXT UNIQUE NOT NULL, " +
                DataContract.RestaurantEntry.COLUMN_RESTAURANT_NAME + " TEXT NOT NULL, " +
                DataContract.RestaurantEntry.COLUMN_RESTAURANT_COVER_IMAGE + "  TEXT NOT NULL, " +
                DataContract.RestaurantEntry.COLUMN_RESTAURANT_LAT + " REAL NOT NULL, " +
                DataContract.RestaurantEntry.COLUMN_RESTAURANT_LONG + " REAL NOT NULL " +
                DataContract.RestaurantEntry.COLUMN_RESTAURANT_CUISINE + " REAL NOT NULL " +
                DataContract.RestaurantEntry.COLUMN_RESTAURANT_RATINGE + " REAL NOT NULL " +
                DataContract.RestaurantEntry.COLUMN_RESTAURANT_PRICE + "  INTEGER NOT NULL, " +
                " );";


        db.execSQL(SQL_CREATE_CUISINE_TABLE);
        db.execSQL(SQL_CREATE_RESTAURANT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.RestaurantEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataContract.CuisinesEntry.TABLE_NAME);
        onCreate(db);
    }
}
