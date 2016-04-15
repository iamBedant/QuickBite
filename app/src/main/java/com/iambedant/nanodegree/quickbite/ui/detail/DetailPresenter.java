package com.iambedant.nanodegree.quickbite.ui.detail;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by Kuliza-193 on 4/15/2016.
 */
public class DetailPresenter extends BasePresenter<DetailMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;
    @Inject
    public DetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(DetailMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }
}
