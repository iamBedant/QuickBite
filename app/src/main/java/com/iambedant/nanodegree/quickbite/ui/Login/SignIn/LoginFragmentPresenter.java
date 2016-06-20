package com.iambedant.nanodegree.quickbite.ui.Login.SignIn;

import android.content.ContentValues;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.data.local.persistent.DataContract;
import com.iambedant.nanodegree.quickbite.data.model.Favourite;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Logger;

import javax.inject.Inject;

/**
 * Created by Kuliza-193 on 4/9/2016.
 */
public class LoginFragmentPresenter extends BasePresenter<LoginFragmentMvpView> {
    private final DataManager mDataManager;
    String TAG = LoginFragmentPresenter.class.getSimpleName();

    @Inject
    public LoginFragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(LoginFragmentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void AddFavourites(final Favourite mRestaurant) {
        Logger.d(TAG, "Adding Favourite");
       if(!mDataManager.isRestaurantPresent(mRestaurant.getRestaurantId())) {
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


            mDataManager.addFavourites(values);
        }


    }
}

