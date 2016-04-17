package com.iambedant.nanodegree.quickbite.data.local.persistent;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Kuliza-193 on 4/16/2016.
 */
public class DataContract {
    public static final String CONTENT_AUTHORITY = "com.iambedant.nanodegree.quickbite";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final String PATH_CUISINES = "cuisines";
    public static final String PATH_RESTAURANTS = "restaurants";

    public static final class CuisinesEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CUISINES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CUISINES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CUISINES;

        // Table name
        public static final String TABLE_NAME = "cuisines";


        public static final String COLUMN_CUISINE_NAME = "cuisine_name";
        public static final String COLUMN_CUISINE_ID = "cuisine_unique_id";
        public static final String COLUMN_IS_FAVOURITE = "cuisine_is_favourite";


        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class RestaurantEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESTAURANTS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESTAURANTS;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESTAURANTS;

        public static final String TABLE_NAME = "restaurants";


        public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
        public static final String COLUMN_RESTAURANT_NAME = "restaurant_name";
        public static final String COLUMN_RESTAURANT_COVER_IMAGE = "restaurant_cover_image";
        public static final String COLUMN_RESTAURANT_CUISINE = "restaurant_cuisine";
        public static final String COLUMN_RESTAURANT_ADDRESS = "restaurant_address";
        public static final String COLUMN_RESTAURANT_LAT = "restaurant_lat";
        public static final String COLUMN_RESTAURANT_LONG = "restaurant_long";
        public static final String COLUMN_RESTAURANT_RATINGE = "restaurant_rating";
        public static final String COLUMN_RESTAURANT_PRICE = "restaurant_price";
        public static final String COLUMN_RESTAURANT_PHONE = "restaurant_phone";

        public static Uri buildRestaurantUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
