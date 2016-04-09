package com.iambedant.nanodegree.quickbite.ui.Login.Register;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Kuliza-193 on 4/9/2016.
 */
public class RegisterFragmentPresenter extends BasePresenter<RegisterFragmentMvpView> {
    private final DataManager mDataManager;

    @Inject
    public RegisterFragmentPresenter(DataManager dataManager){
        mDataManager = dataManager;
    }

    @Override
    public void attachView(RegisterFragmentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}

