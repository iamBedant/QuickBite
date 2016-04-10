package com.iambedant.nanodegree.quickbite.ui.Login;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.Login.Register.RegisterFragment;
import com.iambedant.nanodegree.quickbite.ui.Login.SignIn.LoginFragment;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    @Bind(R.id.tabs)
    TabLayout mTablayoutContainer;

    @Bind(R.id.viewpager)
    ViewPager mViewPagerContainer;

    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter.attachView(this);
        setupViewPager(mViewPagerContainer);
        mTablayoutContainer.setupWithViewPager(mViewPagerContainer);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "Sign In");
        adapter.addFragment(new RegisterFragment(), "Sign Up");
        viewPager.setAdapter(adapter);
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
