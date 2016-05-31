package com.iambedant.nanodegree.quickbite.ui.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.Reviews.UserReview;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant_;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.detail.DetailActivity;
import com.iambedant.nanodegree.quickbite.ui.detail.DetailMvpView;
import com.iambedant.nanodegree.quickbite.ui.detail.DetailPresenter;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.NetworkUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.iambedant.nanodegree.quickbite.R.id.map;

public class RestaurantActivity extends BaseActivity implements DetailMvpView, View.OnClickListener, OnMapReadyCallback {
    private String TAG = DetailActivity.class.getSimpleName();


    @Bind(R.id.container)
    ImageView mImageViewCover;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.ll_review)
    LinearLayout mLinearlayoutReviewContainer;

    @Bind(R.id.tv_cuisines)
    TextView mTextViewCuisines;
    @Bind(R.id.tv_cost)
    TextView mTextViewCosts;
    @Bind(R.id.tv_delivery)
    TextView mTextViewDelivery;
    @Bind(R.id.tv_address)
    TextView mTextViewAddress;
    @Bind(R.id.btn_order_now)
    Button mButtonOrderNow;
    @Bind(R.id.scroll)
    NestedScrollView mScrollView;


    Restaurant_ mRestaurant;
    Context mContext;
    @Inject
    DetailPresenter mDetailPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);


     //   postponeEnterTransition();

        mContext = this;
        mDetailPresenter.attachView(this);
        final Intent intent = getIntent();
        if (intent.hasExtra(Constants.CURRENT_RESTAURANT)) {
            mRestaurant = getIntent().getExtras().getParcelable(Constants.CURRENT_RESTAURANT);
        }
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);
        bindUi();
        if (savedInstanceState == null) {
            initApiCall();
        } else {

        }

    }

    public void initApiCall() {
        if (NetworkUtil.isNetworkConnected(mContext)) {
            mDetailPresenter.getReviews(mRestaurant.getId());
        } else {
            //TODO: Show error on the Review Layout;
        }
    }


    private void bindUi() {
        Glide.with(this)
                .load(mRestaurant.getFeaturedImage())
                .override(480, 400)
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                       startPostponedEnterTransition();
//                        return true;
//                    }
//                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .priority(Priority.IMMEDIATE)
                .into(mImageViewCover);

        mTextViewAddress.setText(mRestaurant.getLocation().getAddress());
        mTextViewCosts.setText("Rs "+ mRestaurant.getAverageCostForTwo() + "For two people(Approx) ");

        mTextViewCuisines.setText(mRestaurant.getCuisines());
        if (mRestaurant.getHasOnlineDelivery() == 0) {
            mTextViewDelivery.setText(R.string.delivery_available_text);
            // mTextViewDelivery.setTextColor(ContextCompact.);
            if (mRestaurant.getIsDeliveringNow() == 1) {
                mButtonOrderNow.setVisibility(View.VISIBLE);
            }

        } else {
            mTextViewDelivery.setText(R.string.delivery_not_available);
        }
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


            }
        });
        //TODO: use a fallback image in case of Glide error
        Glide.with(mContext)
                .load(review.getReview().getUser().getProfileImage())
                .into(mImageViewProfilepic);
        mLinearlayoutReviewContainer.addView(reviewView);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(Double.parseDouble(mRestaurant.getLocation().getLatitude()), Double.parseDouble(mRestaurant.getLocation().getLongitude()))).zoom(15f).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(Double.parseDouble(mRestaurant.getLocation().getLatitude()), Double.parseDouble(mRestaurant.getLocation().getLongitude())))
                .title(mRestaurant.getName()));
    }
}
