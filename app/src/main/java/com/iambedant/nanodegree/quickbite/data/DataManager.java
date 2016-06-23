package com.iambedant.nanodegree.quickbite.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.Loader;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iambedant.nanodegree.quickbite.data.local.PreferencesHelper;
import com.iambedant.nanodegree.quickbite.data.local.persistent.DataContract;
import com.iambedant.nanodegree.quickbite.data.local.persistent.ProviderHelper;
import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisines;
import com.iambedant.nanodegree.quickbite.data.model.Favourite;
import com.iambedant.nanodegree.quickbite.data.model.Reviews.Reviews;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant_;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.SearchResult;
import com.iambedant.nanodegree.quickbite.data.model.User;
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
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private final PreferencesHelper mPreferencesHelper;
    private final EventPosterHelper mEventPoster;
    private final QuickBiteAPIClient mQuickBiteApiClient;
    private final ProviderHelper mProviderHelper;


    @Inject
    public DataManager(PreferencesHelper preferencesHelper,
                       EventPosterHelper eventPosterHelper,
                       QuickBiteAPIClient quickBiteAPIClient,
                       ProviderHelper providerHelper,
                       FirebaseAuth auth,
                       DatabaseReference databaseReference) {
        mPreferencesHelper = preferencesHelper;
        mEventPoster = eventPosterHelper;
        mQuickBiteApiClient = quickBiteAPIClient;
        mProviderHelper = providerHelper;
        mAuth = auth;
        mDatabase = databaseReference;

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


    public void loginUser(String email, String password) {

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
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_PRICE, mRestaurant.getAverageCostForTwo());
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

    public Cursor getCuisines(String queryString) {
        Cursor mCursor = null;
        mCursor = mProviderHelper.getCuisines(queryString);
        return mCursor;
    }

    public void addFavourites(ContentValues values) {
        mProviderHelper.saveSingleRestaurant(values);

    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public void writeNewUser(String userId, String name, String email, final int TYPE) {
        User user = new User(name, email);
        mDatabase.child("users").child(userId).updateChildren(user.toMap());
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (TYPE == Constants.LOGIN) {
                    getFavouriteRestaurants(mAuth.getCurrentUser().getUid());
                } else {
                    //Invoke
                    Logger.d(TAG, "Register Successfull");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getFavouriteRestaurants(String userId) {
        Logger.d(TAG, "getFavourite Restaurant Called");
        final Query mQuery = mDatabase.child("users").child(userId).child("favourites");


        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Logger.d(TAG, "ParsingHashMap");
                HashMap<String, HashMap<String, String>> mMap = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();
                saveRestaurantsToLocalStorage(mMap);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saveRestaurantsToLocalStorage(HashMap<String, HashMap<String, String>> mMap) {
        if (mMap != null) {
            for (Map.Entry<String, HashMap<String, String>> entry : mMap.entrySet()) {

                HashMap<String, String> favourite = entry.getValue();
                AddFavourites(new Favourite(favourite.get("restaurantId"),
                        favourite.get("restaurantName"),
                        favourite.get("coverImage"),
                        favourite.get("cuisine"),
                        favourite.get("address"),
                        favourite.get("lat"),
                        favourite.get("lon"),
                        favourite.get("rating"),
                        Integer.parseInt(String.valueOf(favourite.get("price")))
                ));
            }
        }
        Logger.d(TAG, "Invoking event Bus");
        //Invoke The Event Bus to notify UI(Login Frgment);

    }

    public void AddFavourites(final Favourite mRestaurant) {
        Logger.d(TAG, "Adding Favourite");
        if (!isRestaurantPresent(mRestaurant.getRestaurantId())) {
            Logger.d(TAG, "Previously Not Present");
            ContentValues values = new ContentValues();

            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_ID, mRestaurant.getRestaurantId());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_NAME, mRestaurant.getRestaurantName());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_COVER_IMAGE, mRestaurant.getCoverImage());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_CUISINE, mRestaurant.getCuisine());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_LAT, mRestaurant.getLat());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_LONG, mRestaurant.getLon());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_ADDRESS, mRestaurant.getAddress());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_RATINGE, mRestaurant.getRating());
            values.put(DataContract.RestaurantEntry.COLUMN_RESTAURANT_PRICE, mRestaurant.getPrice());


            addFavourites(values);
        }


    }

    public void firebaseLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            getFavouriteRestaurants(task.getResult().getUser().getUid());
                        } else {

                        }
                    }
                });
    }

    public void createFirebaseUser(final String email, final String password, final String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            writeNewUser(task.getResult().getUser().getUid(), name, task.getResult().getUser().getEmail(), Constants.REGISTER);
                        } else {
                            Logger.d(TAG, "Failed");
                            //TODO: Use Event Bus to comminucate with UI
                        }
                    }
                });
    }


}
