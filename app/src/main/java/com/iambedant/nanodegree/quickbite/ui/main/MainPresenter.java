package com.iambedant.nanodegree.quickbite.ui.main;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Constants;

import javax.inject.Inject;

import rx.Subscription;
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void updateNavHeader(){
        getMvpView().setUpNavHeader(mDataManager.getPreferencesHelper()
                                                .getString(Constants.USER_NAME,"hello"),
                                    mDataManager.getPreferencesHelper()
                                                .getString(Constants.USER_EMAIL,"hi"));
    }


}
