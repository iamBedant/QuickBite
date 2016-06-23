package com.iambedant.nanodegree.quickbite.util;

import com.iambedant.nanodegree.quickbite.BuildConfig;

/**
 * Created by Kuliza-193 on 4/10/2016.
 */
public class Constants {
    public static final String USER_NAME = "user_name";
    public static final String USER_EMAIL = "user_email";
    public static final String FIREBASE_URL = BuildConfig.MY_FIREBASE_URL;
    public static final String ZOMATO_API_KEY = BuildConfig.ZOMATO_API_KEY;

    public static final String SEARCH_POSITION = "_search_position";

    public static final String CURRENT_RESTAURANT = "current_restaurant";
    public static final String CURRENT_REVIEW = "current_review";

    public static final String TYPE_EXTRA_KEY = "_selected_key";

    public static final int TYPE_TAKE_AWAY = 1;
    public static final int TYPE_DINNER = 2;
    public static final int TYPE_BREAKFAST = 3;
    public static final int TYPE_BAR = 4;
    public static final int TYPE_COFFEE = 5;
    public static final int TYPE_FAVOURITE = 6;


    public static final String REVIEW_COUNT = "5";
    public static final String REVIEW_COUNT_KEY = "count";
    public static final String RESTAURANT_ID_KEY ="res_id";

    public static final String LAST_KNOWN_LAT = "_last_known_lat";
    public static final String LAST_KNOWN_LON = "_last_known_lon";
    public static final String LAST_KNOWN_LOCALITY = "_last_known_locality";


    public static final String LAT_KEY ="lat";
    public static final String LON_KEY ="lon";
    public static final String CATEGORY_KEY ="category";
    public static final String CUISINE_ID ="cuisines";
    public static final int LOCATION_INTERVAL_MILLISECONDS = 5000;

    public static final String BUNDLE_IS_DATA_LOADED= "bundle_is_data_loaded";
    public static final String BUNDLE_LIST_RESTAURANTS = "bundle_list_restaurant";
    public static final String BUNDLE_SELECTED_CUISINE = "bundle_selected_cuisine";
    public static final String BUNDLE_SELECTION_TYPE = "bundle_selection_type";
    public static final String BUNDLE_LOADED_REVIEW = "bundle_loaded_review";
    public static final String BUNDLE_SELECTED_RESTAURANT = "bundle_selected_restaurant";
    public static final String BUNDLE_IS_CUISINE_FILTER_APPLIED = "bundle_is_cuisine_filter_applied";

    public static final int ERROR_TYPE_NETWORK =1;
    public static final int ERROR_TYPE_NO_DATA =2;
    public static final int ERROR_TYPE_DEFAULT =999;
    public static final int LOGIN = 1;
    public static final int REGISTER = 2;

    public static String SEARCH_TERM="_search_term";


}
