package com.iambedant.nanodegree.quickbite.ui.Login;

import android.os.Bundle;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_login);
        mLoginPresenter.attachView(this);
        mLoginPresenter.printValue();
    }

    @Override
    public void showDataEmpty() {

    }

    @Override
    public void showError() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mLoginPresenter.detachView();
    }
}
