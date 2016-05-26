package com.iambedant.nanodegree.quickbite.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.Reviews.UserReview;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant_;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.NetworkUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements DetailMvpView, View.OnClickListener {

    private String TAG = DetailActivity.class.getSimpleName();

    @Bind(R.id.scrim)
    ImageView mScrimView;

    @Bind(R.id.container)
    ImageView mImageViewCover;

    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.ll_review)
    LinearLayout mLinearlayoutReviewContainer;

    Restaurant_ mRestaurant;

    @Inject
    DetailPresenter mDetailPresenter;
    Context mContext;

    private static final float SCRIM_ADJUSTMENT = 0.075f;
    CollapsingToolbarLayout collapsingToolbarLayout;


    private SharedElementCallback sharedElementCallback = new SharedElementCallback() {
        @Override
        public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
            //  super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        @Override
        public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
            //   super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        mContext = this;
        mDetailPresenter.attachView(this);
        final Intent intent = getIntent();
        if (intent.hasExtra(Constants.CURRENT_RESTAURANT)) {
            mRestaurant = getIntent().getExtras().getParcelable(Constants.CURRENT_RESTAURANT);
            loadSharedElement();
        }

        if (savedInstanceState == null) {
            initApiCall();
        } else {

        }

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(mRestaurant.getName());
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));


        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int toolBarHeight = mToolbar.getMeasuredHeight();
                final int statusBarHeight = statusBarHeight(getResources());
                int appBarHeight = mAppBarLayout.getMeasuredHeight();
                appBarHeight = appBarHeight - toolBarHeight;
                final int half = appBarHeight / 2;
                final float factor = (float) 255 / half;
                verticalOffset = verticalOffset - statusBarHeight;
                if ((-verticalOffset) >= half) {
                    Float f = factor * (-verticalOffset - half);
                    mScrimView.getBackground().setAlpha(Math.round(f));
                    Log.d(TAG, "alpha ->" + Math.round(f));
                } else {
                    mScrimView.getBackground().setAlpha(0);
                }
            }
        });

    }

    public void initApiCall() {
        if (NetworkUtil.isNetworkConnected(mContext)) {
            mDetailPresenter.getReviews(mRestaurant.getId());
        } else {
            //TODO: Show error on the Review Layout;
        }
    }

    private void loadSharedElement() {
        mScrimView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));


        Glide.with(this)
                .load(mRestaurant.getFeaturedImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .priority(Priority.IMMEDIATE)
                .into(mImageViewCover);


    }

    private static int statusBarHeight(android.content.res.Resources res) {
        return (int) (24 * res.getDisplayMetrics().density);
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();

    }


    @OnClick(R.id.img_btn_favourite)
    public void saveFavouriteRestaurant() {
        mDetailPresenter.saveRestaurant(mRestaurant);
    }

    @OnClick(R.id.img_btn_direction)
    public void getDirectionToTheRestaurant() {
        mDetailPresenter.deleteRestaurant(mRestaurant.getId());
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mDetailPresenter.detachView();
    }

    @Override
    public void showReviews(List<UserReview> mListReview) {
        for (UserReview review : mListReview) {
            addReviewLayout(review);
        }
    }

    private void addReviewLayout(final UserReview review) {

        View reviewView = getLayoutInflater().inflate(R.layout.item_review, null, false);

        TextView mTextViewName = (TextView) reviewView.findViewById(R.id.tv_name);
        TextView mTextViewFoodieLevel = (TextView) reviewView.findViewById(R.id.tv_foodie_level);
        TextView mTextViewRating = (TextView) reviewView.findViewById(R.id.tv_rating);
        TextView mTextViewReview = (TextView) reviewView.findViewById(R.id.tv_review);
        Button mButtonSeeMore = (Button) reviewView.findViewById(R.id.btn_see_more);

        ImageView mImageViewProfilepic = (ImageView) reviewView.findViewById(R.id.iv_profile_pic);

        mTextViewName.setText(review.getReview().getUser().getName());
        mTextViewFoodieLevel.setText(review.getReview().getUser().getFoodieLevel());
        mTextViewRating.setText(review.getReview().getRating() + "");
        mTextViewReview.setText(review.getReview().getReviewText());
        mButtonSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailPresenter.openDetailReview(review);
            }
        });
        //TODO: use a fallback image in case of Glide error
        Glide.with(mContext)
                .load(review.getReview().getUser().getProfileImage())
                .into(mImageViewProfilepic);
        mLinearlayoutReviewContainer.addView(reviewView);
    }
}
