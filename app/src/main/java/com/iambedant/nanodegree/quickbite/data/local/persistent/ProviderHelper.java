package com.iambedant.nanodegree.quickbite.data.local.persistent;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

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

    @Inject
    public ProviderHelper(@ApplicationContext Context context) {
        mContentResolver = context.getContentResolver();
    }

    public ContentResolver getContentResolver() {
        return mContentResolver;
    }

    public void saveCusinesToDb(Vector<ContentValues> cVVector) {

        if (cVVector.size() > 0) {
            Logger.d("Bulk Insert before", cVVector.size() + "");
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            int count = mContentResolver.bulkInsert(DataContract.CuisinesEntry.CONTENT_URI, cvArray);
            Logger.d("Bulk Insert After", count + "");
        }
    }

    public void saveFavouriteRestaurant(ContentValues values) {
        Uri insertedUri = mContentResolver.insert(
                DataContract.RestaurantEntry.CONTENT_URI,
                values
        );
        Logger.d("Insert_Test", insertedUri.toString());
    }

    public void updateFavouriteRestaurant() {

    }

    public void deleteFavouriteRestaurant() {

    }
//    public Observable<Restaurant_> getFavouriteRestaurants(){
//        Observable<Restaurant_>  favouriteRestaurants;
//
//        return favouriteRestaurants;
//    }

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
}
