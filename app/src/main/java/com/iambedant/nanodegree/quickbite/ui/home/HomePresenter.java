package com.iambedant.nanodegree.quickbite.ui.home;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Logger;

import javax.inject.Inject;

/**
 * Created by Kuliza-193 on 5/13/2016.
 */
public class HomePresenter extends BasePresenter<HomeMvpView> {

    String TAG = HomePresenter.class.getSimpleName();
    private final DataManager mDataManager;
    @Inject
    public HomePresenter(DataManager dataManager) {
        Logger.d(TAG,"Presenter Created");
        mDataManager = dataManager;
    }

    public void loadLastKnownLocation() {
        getMvpView().showLocation(mDataManager.getLastKnownLocation());
    }

    public void saveLocation(Double lat, Double lon, String subLocality) {
        mDataManager.saveCurrentLocation(lat,lon,subLocality);
        getMvpView().showLocation(subLocality);
    }

    public void getUserName() {
        mDataManager.getUserName();
    }
}
