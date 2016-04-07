package com.iambedant.nanodegree.quickbite.Ui.Login;

import android.os.Bundle;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.Ui.base.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginMvpView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void showDataEmpty() {

    }

    @Override
    public void showError() {

    }
}
