package com.iambedant.nanodegree.quickbite.ui.Login;

import android.util.Log;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by Kuliza-193 on 4/7/2016.
 */
public class LoginPresenter extends BasePresenter<LoginMvpView> {

    private final DataManager mDataManager;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();

    }


    public void printValue()
    {
        Log.d("myTest","Success");
    }
}
