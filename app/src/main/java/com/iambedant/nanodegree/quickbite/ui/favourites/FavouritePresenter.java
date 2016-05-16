package com.iambedant.nanodegree.quickbite.ui.favourites;

import android.database.Cursor;
import android.support.v4.content.Loader;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Kuliza-193 on 5/14/2016.
 */
public class FavouritePresenter extends BasePresenter<FavouriteMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public FavouritePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(FavouriteMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public Loader<Cursor> getFavouriteRestaurants() {
        return mDataManager.getFavouriteRestaurants();

    }

}
