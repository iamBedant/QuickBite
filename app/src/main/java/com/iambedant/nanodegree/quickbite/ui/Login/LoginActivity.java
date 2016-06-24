package com.iambedant.nanodegree.quickbite.ui.Login;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.facebook.FacebookSdk;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.events.EventLoginSuccessfull;
import com.iambedant.nanodegree.quickbite.ui.Login.Register.RegisterFragment;
import com.iambedant.nanodegree.quickbite.ui.Login.SignIn.LoginFragment;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.home.Home;
import com.iambedant.nanodegree.quickbite.util.EventPosterHelper;
import com.iambedant.nanodegree.quickbite.util.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

    @Bind(R.id.iv_tab_header_signin)
    ImageSwitcher mImageViewHeaderSignIn;
    //
    @Inject
    EventPosterHelper mEventPosterHelper;

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
        EventBus.getDefault().register(this);

        mImageViewHeaderSignIn.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView mImageView = new ImageView(getApplicationContext());
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return mImageView;
            }
        });


        mImageViewHeaderSignIn.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));

        mImageViewHeaderSignIn.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));
        mImageViewHeaderSignIn.setImageResource(R.drawable.login_bg);
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
//                    if(positionOffset>=.5){
//                        mImageViewHeaderSignIn.setImageResource(R.drawable.login_bg);
//
//                    }
//                    else {

//                    }
                }


            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {

                    mImageViewHeaderSignIn.setImageResource(R.drawable.login_bg);
                } else {
                    mImageViewHeaderSignIn.setImageResource(R.drawable.signup_bg);
                }
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
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(EventLoginSuccessfull event) {
        Logger.d(TAG, "This is inside the event Bus !!!");
        if (event.isSuccessfull) {
            Logger.d(TAG, "This is inside the event Bus !!!, Login SuccessFull");
            Intent intent = new Intent(mContext, Home.class);
            startActivity(intent);
            finish();
        }else {
            Logger.d(TAG,event.message);
        }
    }
}
