package com.iambedant.nanodegree.quickbite.ui.picklocation;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by iamBedant on 7/1/2016.
 */
public class LocationPresenter extends BasePresenter<LocationMvpView> {
    private final DataManager mDataManager;
    private final String TAG = LocationPresenter.class.getSimpleName();

    @Inject
    public LocationPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(LocationMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

}
