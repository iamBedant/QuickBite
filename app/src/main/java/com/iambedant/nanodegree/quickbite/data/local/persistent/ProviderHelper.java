package com.iambedant.nanodegree.quickbite.data.local.persistent;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.iambedant.nanodegree.quickbite.injection.ApplicationContext;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.util.Vector;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Kuliza-193 on 4/18/2016.
 */
@Singleton
public class ProviderHelper
{

    ContentResolver mContentResolver;

    @Inject
    public ProviderHelper(@ApplicationContext Context context) {
        mContentResolver = context.getContentResolver();
    }

    public ContentResolver getContentResolver()
    {
        return mContentResolver;
    }

    public void saveCusinesToDb( Vector<ContentValues> cVVector){

        if ( cVVector.size() > 0 ) {
            Logger.d("Bulk Insert before", cVVector.size()+"");
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);
            int count = mContentResolver.bulkInsert(DataContract.CuisinesEntry.CONTENT_URI, cvArray);
            Logger.d("Bulk Insert After", count+"");
        }
    }

    public void saveFavouriteRestaurant(){

    }
    public void updateFavouriteRestaurant(){

    }
    public void deleteFavouriteRestaurant(){

    }
//    public Observable<Restaurant_> getFavouriteRestaurants(){
//        Observable<Restaurant_>  favouriteRestaurants;
//
//        return favouriteRestaurants;
//    }
}
