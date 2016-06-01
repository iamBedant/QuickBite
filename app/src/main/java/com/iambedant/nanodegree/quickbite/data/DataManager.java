package com.iambedant.nanodegree.quickbite.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.content.Loader;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.iambedant.nanodegree.quickbite.data.local.PreferencesHelper;
import com.iambedant.nanodegree.quickbite.data.local.persistent.DataContract;
import com.iambedant.nanodegree.quickbite.data.local.persistent.ProviderHelper;
import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisines;
import com.iambedant.nanodegree.quickbite.data.model.Reviews.Reviews;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant_;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.SearchResult;
import com.iambedant.nanodegree.quickbite.data.remote.FireBaseClient;
import com.iambedant.nanodegree.quickbite.data.remote.QuickBiteAPIClient;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.EventPosterHelper;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Action0;

@Singleton
public class DataManager {

    private final String TAG = DataManager.class.getSimpleName();

    private final PreferencesHelper mPreferencesHelper;
    private final EventPosterHelper mEventPoster;
    private final FireBaseClient mFireBaseClient;
    private final QuickBiteAPIClient mQuickBiteApiClient;
    private final ProviderHelper mProviderHelper;


    @Inject
    public DataManager(PreferencesHelper preferencesHelper, EventPosterHelper eventPosterHelper, FireBaseClient fireBaseClient, QuickBiteAPIClient quickBiteAPIClient, ProviderHelper providerHelper) {
        mPreferencesHelper = preferencesHelper;
        mEventPoster = eventPosterHelper;
        mFireBaseClient = fireBaseClient;
        mQuickBiteApiClient = quickBiteAPIClient;
        mProviderHelper = providerHelper;
    }


    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    /// Helper method to post events from doOnCompleted.
    private Action0 postEventAction(final Object event) {
        return new Action0() {
            @Override
            public void call() {
                mEventPoster.postEventSafely(event);
            }
        };
    }


    public Boolean createCustomUser(final String email, final String password) {
        final Boolean status = false;
        mFireBaseClient.getmFireBaseRef().createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                loginUser(email, password);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error

                Log.d("FireBase", firebaseError.toString());
            }
        });

        return status;
    }

    public void loginUser(String email, String password) {
        mFireBaseClient.getmFireBaseRef().authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                Log.d("FireBase", "SuccessfullyLoggedIn--->" + authData.getUid());
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                Log.d("FireBase", "error Logging in");
            }
        });
    }

    public Observable<SearchResult> getSearchData(Map<String, String> queryParams) {
        return mQuickBiteApiClient.getSearchResult(queryParams);
    }

    public Observable<Cuisines> getNearbyCuisines(Map<String, String> queryParams) {
        return mQuickBiteApiClient.getCuisines(queryParams);
    }

    public void saveCusinesToDb(Vector<ContentValues> cVVector) {

        mProviderHelper.deleteAllCuisines();
        Logger.d(TAG, "Old Cuisines Deleted");
        mProviderHelper.saveAllCuisines(cVVector);
        Logger.d(TAG, "New Cuisines Stored");
    }

    public void saveFavouriteRestaurant(Restaurant_ mRestaurant) {
        if (mProviderHelper.isRestaurantPresent(mRestaurant.getId())) {
            Logger.d(TAG, "Already in Favourite");
        } else {
            ContentValues values = new ContentValues();
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_ID, mRestaurant.getId());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_NAME, mRestaurant.getName());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_COVER_IMAGE, mRestaurant.getFeaturedImage());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_CUISINE, mRestaurant.getCuisines());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_LAT, mRestaurant.getLocation().getLatitude());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_LONG, mRestaurant.getLocation().getLongitude());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_ADDRESS, mRestaurant.getLocation().getAddress());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_RATINGE, mRestaurant.getUserRating().getAggregateRating());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_PRICE, mRestaurant.getPriceRange());
            mProviderHelper.saveSingleRestaurant(values);
        }
    }

    public void deleteFavouriteRestaurant(String id) {
        mProviderHelper.deleteSingleRestaurant(id);
    }

    public void saveCurrentLocation(Double lat, Double lon, String locality) {
        mPreferencesHelper.putString(Constants.LAST_KNOWN_LOCALITY, locality);
        mPreferencesHelper.putDouble(Constants.LAST_KNOWN_LAT, lat);
        mPreferencesHelper.putDouble(Constants.LAST_KNOWN_LON, lon);

    }

    public String getLastKnownLocation() {
        return mPreferencesHelper.getString(Constants.LAST_KNOWN_LOCALITY, "");
    }

    public Loader<Cursor> getFavouriteRestaurants() {
        return mProviderHelper.getFavouriteRestaurants();
    }

    public Observable<Reviews> getReviews(HashMap<String, String> params) {
        return mQuickBiteApiClient.getReviews(params);
    }

    public String getLon() {
        return mPreferencesHelper.getDouble(Constants.LAST_KNOWN_LON, 0.0) + "";
    }


    public String getLat() {
        return mPreferencesHelper.getDouble(Constants.LAST_KNOWN_LAT, 0.0) + "";
    }

    public boolean isRestaurantPresent(String id) {
        return mProviderHelper.isRestaurantPresent(id);
    }

    public Cursor getAllCuisines() {
        Cursor mCursor = null;
        mCursor = mProviderHelper.getAllCuisines();
        return mCursor;
    }
}
