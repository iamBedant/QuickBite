package com.iambedant.nanodegree.quickbite.ui.review;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.Reviews.Review;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.Logger;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FullReview extends BaseActivity implements ReviewMvpView {


    public String TAG = FullReview.class.getSimpleName();
    @Inject
    ReviewPrsenter mReviewPresenter;
    Context mContext;
    Review mReview;


    @Bind(R.id.tv_name)
    TextView mTextViewName;

    @Bind(R.id.tv_foodie_level)
    TextView mTextViewFoodieLevel;

    @Bind(R.id.tv_review)
    TextView mTextViewReview;

    @Bind(R.id.btn_view_profile)
    Button mButtonProfile;

    @Bind(R.id.iv_profile_pic)
    ImageView mImageViewProfile;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.tv_toolbar_title)
    TextView mToolbarTitle;

    Activity host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_review);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mContext = this;
        initChromeCustomTab();
        host = this;
        if (getIntent().hasExtra(Constants.CURRENT_REVIEW)) {
            mReview = getIntent().getParcelableExtra(Constants.CURRENT_REVIEW);
        }
        mReviewPresenter.attachView(this);
        bindUi();

        setUpToolbar();

    }

    public void setUpToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbarTitle.setText(R.string.settings);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });
    }

    public void bindUi() {
        Glide.with(host)
                .load(mReview.getUser().getProfileImage())
                .override(480, 400)
                .into(mImageViewProfile);

        //todo: Add a slide animation to  Review Content

        mTextViewName.setText(mReview.getUser().getName());
        mTextViewFoodieLevel.setText(mReview.getUser().getFoodieLevel());
        mTextViewReview.setText(mReview.getReviewText());

    }

    @OnClick(R.id.btn_view_profile)
    public void openReviewerProfile() {
//        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mReview.getUser().getProfileUrl()));
//        startActivity(browserIntent);
//
        Logger.d(TAG, "User Profile");
        launchUrl(mContext, mReview.getUser().getProfileUrl());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mReviewPresenter.detachView();
    }


    public void launchUrl(Context mContext, String url) {
        Logger.d(TAG, url);
        customTabsIntent.launchUrl((Activity) mContext, Uri.parse(url));

    }

}
