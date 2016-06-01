package com.iambedant.nanodegree.quickbite.data.local.persistent;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.iambedant.nanodegree.quickbite.injection.ApplicationContext;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.util.Vector;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Kuliza-193 on 4/18/2016.
 */
@Singleton
public class ProviderHelper {

    ContentResolver mContentResolver;
    public static String TAG = ProviderHelper.class.getSimpleName();
    Context mContext;

    @Inject
    public ProviderHelper(@ApplicationContext Context context) {
        mContentResolver = context.getContentResolver();
        mContext = context;
    }

    public ContentResolver getContentResolver() {
        return mContentResolver;
    }


/*----------------------- Cuisines Helper Methods ---------------------------*/


    public void saveAllCuisines(Vector<ContentValues> cVVector) {

        if (cVVector.size() > 0) {
            Logger.d(TAG, cVVector.size() + "");
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            int count = mContentResolver.bulkInsert(DataContract.CuisinesEntry.CONTENT_URI, cvArray);
            Logger.d(TAG, count + "");
        }
    }

    public void deleteAllCuisines(){
        mContentResolver.delete(DataContract.CuisinesEntry.CONTENT_URI,
                null,
                null);
    }

    //Don't know whether I am gonna use this method or not.
    //Right now I am on a boring flight and I forgot my earphones at home,
    //so writing this method in case I will need it in future.

    public Cursor getSingleCuisine(String id){
        Cursor cursor =mContentResolver.query(
                DataContract.CuisinesEntry.buildSingleCuisineUri(id),
                null,
                DataContract.CuisinesEntry.COLUMN_CUISINE_ID + " = ?",
                new String[]{id},
                null

        );

        return cursor;
    }



/*----------------------- Restaurant Helper Methods ---------------------------*/

    public void saveSingleRestaurant(ContentValues values) {
        Uri insertedUri = mContentResolver.insert(
                DataContract.RestaurantEntry.CONTENT_URI,
                values
        );
        Logger.d(TAG, insertedUri.toString());
    }

    public void updateSingleRestaurant(ContentValues values, String id) {
        int count = mContentResolver.update(DataContract.RestaurantEntry.buildRestaurantUri(id),
                values,
                DataContract.RestaurantEntry.COLUMN_RESTAURANT_ID + "== ?",
                new String[]{id});
        if (count > 0) {
            Logger.d(TAG, "Update Success");
        }
    }

    public void deleteSingleRestaurant(String id) {

        if (isRestaurantPresent(id)) {
            mContentResolver.delete(DataContract.RestaurantEntry.buildRestaurantUri(id),
                    DataContract.RestaurantEntry.COLUMN_RESTAURANT_ID + " == ?",
                    new String[]{id}
            );
        } else {
            Logger.d(TAG, "Restaurant not present");
        }


    }

    public Boolean isRestaurantPresent(String id) {
        Boolean isPresent;

        Cursor cursor = getContentResolver().query(
                DataContract.RestaurantEntry.buildRestaurantUri(id),
                new String[]{DataContract.RestaurantEntry._ID},
                DataContract.RestaurantEntry.COLUMN_RESTAURANT_ID + " = ?",
                new String[]{id},
                null);
        ;

        if (cursor.moveToFirst()) {
            isPresent = true;
        } else {
            isPresent = false;
        }
        return isPresent;
    }

    public Cursor getSingleRestaurant(String id) {
        Cursor cursor = mContentResolver.query(DataContract.RestaurantEntry.buildRestaurantUri(id),
                null,
                DataContract.RestaurantEntry.COLUMN_RESTAURANT_ID + " = ?",
                new String[]{id},
                null
        );

        return cursor;
    }

    public Cursor getAllRestaurants(){
        Cursor cursor = mContentResolver.query(DataContract.RestaurantEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        return cursor;
    }

    public Loader<Cursor> getFavouriteRestaurants() {
        return new CursorLoader(mContext,DataContract.RestaurantEntry.CONTENT_URI,null,null,null,null);
    }

    public Cursor getAllCuisines(){
        return mContentResolver.query(DataContract.CuisinesEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }
}
