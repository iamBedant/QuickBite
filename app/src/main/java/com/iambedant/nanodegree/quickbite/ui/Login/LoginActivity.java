package com.iambedant.nanodegree.quickbite.ui.Login;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.Login.Register.RegisterFragment;
import com.iambedant.nanodegree.quickbite.ui.Login.SignIn.LoginFragment;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginMvpView {

    private String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.tabs)
    TabLayout mTablayoutContainer;

    @Bind(R.id.viewpager)
    ViewPager mViewPagerContainer;

    @Bind(R.id.scrim)
    ImageView mImageViewScrim;

    Context mContext;

    @Inject
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter.attachView(this);
        mContext = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        setupViewPager(mViewPagerContainer);
        mTablayoutContainer.setupWithViewPager(mViewPagerContainer);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "Sign In");
        adapter.addFragment(new RegisterFragment(), "Sign Up");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    final int composeColor = blendColors(ContextCompat.getColor(mContext, R.color.signin_scrim_color), ContextCompat.getColor(mContext, R.color.signup_scrim_color), positionOffset);
                    mImageViewScrim.setBackgroundColor(composeColor);
                } else {
                    final int composeColor = blendColors(ContextCompat.getColor(mContext, R.color.signup_scrim_color), ContextCompat.getColor(mContext, R.color.signin_scrim_color), positionOffset);
                    mImageViewScrim.setBackgroundColor(composeColor);
                }


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.argb(150, (int) r, (int) g, (int) b);
    }

    @Override
    public void showDataEmpty() {

    }

    @Override
    public void showError() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }
}
