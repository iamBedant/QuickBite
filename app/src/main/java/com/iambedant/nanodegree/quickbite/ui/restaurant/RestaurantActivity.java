package com.iambedant.nanodegree.quickbite.ui.restaurant;

import android.app.Activity;
import android.app.ActivityOptions;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
import com.iambedant.nanodegree.quickbite.events.RestaurantAddOrDeleteSuccessful;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.review.FullReview;
import com.iambedant.nanodegree.quickbite.ui.widget.WidgetProvider;
import com.iambedant.nanodegree.quickbite.util.ColorUtils;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.GlideUtils.GlideUtils;
import com.iambedant.nanodegree.quickbite.util.Logger;
import com.iambedant.nanodegree.quickbite.util.NetworkUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.iambedant.nanodegree.quickbite.R.id.map;

public class RestaurantActivity extends BaseActivity implements RestaurantMvpView, View.OnClickListener, OnMapReadyCallback {
    private String TAG = RestaurantActivity.class.getSimpleName();


    @Bind(R.id.container)
    ImageView mImageViewCover;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.ll_review)
    LinearLayout mLinearlayoutReviewContainer;

    @Bind(R.id.pb_review_loader)
    ProgressBar mProgressBarReviewLoader;

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
    @Bind(R.id.img_btn_favourite)
    ImageButton mimageButtonFavourite;

    @Bind(R.id.scrim)
    ImageView mScrim;
    @Bind(R.id.tv_toolbar_title)
    TextView mTextViewToolBarTitle;

    Activity host;

    Restaurant_ mRestaurant;

    Boolean isReviewLoaded = false;

    Context mContext;
    @Inject
    RestaurantPresenter mDetailPresenter;

    ArrayList<UserReview> mListUserReviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        initChromeCustomTab();
        EventBus.getDefault().register(this);
        mContext = this;
        host = this;
        mDetailPresenter.attachView(this);
        final Intent intent = getIntent();
        if (intent.hasExtra(Constants.CURRENT_RESTAURANT)) {
            mRestaurant = getIntent().getExtras().getParcelable(Constants.CURRENT_RESTAURANT);
        }
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);

        setUpToolbar();
        if (savedInstanceState == null) {
            initApiCall();
        } else {
            mRestaurant = savedInstanceState.getParcelable(Constants.BUNDLE_SELECTED_RESTAURANT);
            if (savedInstanceState.getBoolean(Constants.BUNDLE_LOADED_REVIEW)) {
                ArrayList<UserReview> reviewList = savedInstanceState.getParcelableArrayList(Constants.BUNDLE_LOADED_REVIEW);
                showReviews(reviewList);
            } else {
                initApiCall();
            }
        }

        bindUi();

    }

    private void setUpToolbar() {

        if (mToolbar != null) {

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mTextViewToolBarTitle.setText(mRestaurant.getName());
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }


    public void initApiCall() {
        if (NetworkUtil.isNetworkConnected(mContext)) {
            mProgressBarReviewLoader.setVisibility(View.VISIBLE);
            mDetailPresenter.getReviews(mRestaurant.getId());
        } else {
            showErrorView(Constants.ERROR_TYPE_NETWORK);
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(Constants.BUNDLE_IS_DATA_LOADED, isReviewLoaded);
        if (isReviewLoaded) {
            outState.putParcelableArrayList(Constants.BUNDLE_LOADED_REVIEW, mListUserReviews);
        } else {
            outState.putParcelable(Constants.BUNDLE_SELECTED_RESTAURANT, mRestaurant);
        }
    }

    private void bindUi() {
        Glide.with(this)
                .load(mRestaurant.getFeaturedImage())
                .override(480, 400)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        final Bitmap bitmap = GlideUtils.getBitmap(resource);
//                        final int twentyFourDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                                24, mContext.getResources().getDisplayMetrics());
                        Palette.from(bitmap)
                                // .setRegion(0, 0, bitmap.getWidth() - 1, twentyFourDip)
                                .generate(new Palette.PaletteAsyncListener() {
                                    @Override
                                    public void onGenerated(Palette palette) {
//                                        final Palette.Swatch topColor =
//                                                ColorUtils.getMostPopulousSwatch(palette);

                                        int mutedDark = palette.getDarkMutedColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            getWindow().setStatusBarColor(ColorUtils.getDarkerColor(mutedDark));
                                            mToolbar.setBackgroundColor(mutedDark);
                                            mScrim.setBackgroundColor(mutedDark);
                                        }
                                    }
                                });


                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .priority(Priority.IMMEDIATE)
                .into(mImageViewCover);


        mImageViewCover.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //todo: set the color using pallete APi

                mScrim.getBackground().setAlpha(0);
                mToolbar.getBackground().setAlpha(0);
                scrollChange();
            }
        });


        if (mDetailPresenter.isRestaurantPresent(mRestaurant.getId())) {
            mimageButtonFavourite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_black_24dp));

        } else {
            mimageButtonFavourite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_border_black_24dp));

        }


        mTextViewAddress.setText(mRestaurant.getLocation().getAddress());
        mTextViewCosts.setText("Rs " + mRestaurant.getAverageCostForTwo() + "For two people (Approx) ");

        mTextViewCuisines.setText(mRestaurant.getCuisines());
        if (mRestaurant.getHasOnlineDelivery() == 0) {
            mTextViewDelivery.setText(R.string.delivery_available_text);
            mTextViewDelivery.setTextColor(ContextCompat.getColor(mContext, R.color.color_available));
            if (mRestaurant.getIsDeliveringNow() == 1) {
                mButtonOrderNow.setVisibility(View.VISIBLE);
            }

        } else {
            mTextViewDelivery.setText(R.string.delivery_not_available);
            mTextViewDelivery.setTextColor(ContextCompat.getColor(mContext, R.color.colorTextSecondary));
        }
    }

    public void scrollChange() {
        final int scrollOrigin = (int) (200 * getResources().getDisplayMetrics().density);
        final int toolbarHeight = (mToolbar.getMeasuredHeight());
        final int scrimStart = scrollOrigin / 2;
        final int scrimGap = (scrollOrigin - toolbarHeight) - scrimStart;


        final float factor = (float) 255 / scrimGap;
        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int dy = scrollY - oldScrollY;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    if (scrollY > oldScrollY) {
                        //Scrolling Up
                        mImageViewCover.setTranslationY(Math.max(-scrollOrigin, mImageViewCover.getTranslationY() - dy / 2));
                    } else {
                        //Scrolling Down
                        mImageViewCover.setTranslationY(Math.min(0, mImageViewCover.getTranslationY() - dy / 2));
                    }
                }
                if ((scrollY) >= scrimStart) {
                    Float f = factor * (scrollY - (scrimStart));

                    if (f > 255) {
                        f = (float) 255;
                        getSupportActionBar().setElevation(16);
                    } else {
                        getSupportActionBar().setElevation(0);
                    }
                    mScrim.getBackground().setAlpha(Math.round(f));
                    mToolbar.getBackground().setAlpha(Math.round(f));
                } else {
                    mScrim.getBackground().setAlpha(0);
                    mToolbar.getBackground().setAlpha(0);
                }

            }
        });

    }


    @Override
    public void onClick(View v) {

    }

    @Subscribe
    public void onEvent(RestaurantAddOrDeleteSuccessful event) {
        Logger.d(TAG, "This is inside the event Bus !!!");
        if (event.isSuccessfull) {
            updateAllWidgets();
        } else {

        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mDetailPresenter.detachView();
    }

    @Bind(R.id.btn_show_all_review)
    Button mButtonShowAllReview;

    @Override
    public void showReviews(ArrayList<UserReview> mListReview) {
        mProgressBarReviewLoader.setVisibility(View.GONE);
        if (mListReview.size() > 0) {
            for (UserReview review : mListReview) {
                addReviewLayout(review);
            }
        } else {
            addNoReviewLayout();
        }

    }


    private void addNoReviewLayout() {
        mButtonShowAllReview.setVisibility(View.GONE);
        View noReviewView = getLayoutInflater().inflate(R.layout.no_data_found, null, false);
        Button mButtonAction = (Button) noReviewView.findViewById(R.id.btn_action);
        mButtonAction.setVisibility(View.GONE);
        TextView mTextViewMessage = (TextView) noReviewView.findViewById(R.id.tv_text);
        mTextViewMessage.setText(R.string.no_review);
        mLinearlayoutReviewContainer.addView(noReviewView);

    }

    private void addReviewLayout(final UserReview review) {
        mButtonShowAllReview.setVisibility(View.VISIBLE);

        View reviewView = getLayoutInflater().inflate(R.layout.item_review, null, false);

        TextView mTextViewName = (TextView) reviewView.findViewById(R.id.tv_name);
        TextView mTextViewFoodieLevel = (TextView) reviewView.findViewById(R.id.tv_foodie_level);
        TextView mTextViewRating = (TextView) reviewView.findViewById(R.id.tv_rating);
        TextView mTextViewReview = (TextView) reviewView.findViewById(R.id.tv_review);
        Button mButtonSeeMore = (Button) reviewView.findViewById(R.id.btn_see_more);

        final ImageView mImageViewProfilepic = (ImageView) reviewView.findViewById(R.id.iv_profile_pic);

        mTextViewName.setText(review.getReview().getUser().getName());
        mTextViewFoodieLevel.setText(review.getReview().getUser().getFoodieLevel());
        mTextViewRating.setText(review.getReview().getRating() + "");
        mTextViewReview.setText(review.getReview().getReviewText());
        mButtonSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, FullReview.class);
                intent.putExtra(Constants.CURRENT_REVIEW, review.getReview());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Pair<View, String> profileImagePair = new Pair<View, String>(mImageViewProfilepic, getString(R.string.reviewer_transition_name));
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(host, profileImagePair).toBundle());

                } else {
                    startActivity(intent);
                }

            }
        });
        //TODO: use a fallback image in case of Glide error
        Glide.with(mContext)
                .load(review.getReview().getUser().getProfileImage())
                .override(480, 400)
                .into(mImageViewProfilepic);
        mLinearlayoutReviewContainer.addView(reviewView);


        mButtonShowAllReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] url = mRestaurant.getUrl().split("\\?");
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url[0] + "/reviews"));
//                startActivity(browserIntent);
                launchUrl(mContext, url[0] + "/reviews");

            }
        });
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


    @OnClick(R.id.img_btn_favourite)
    public void saveFavouriteRestaurant() {

        if (mDetailPresenter.isRestaurantPresent(mRestaurant.getId())) {
            mDetailPresenter.deleteRestaurant(mRestaurant.getId());
            mimageButtonFavourite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_border_black_24dp));

        } else {
            mDetailPresenter.saveRestaurant(mRestaurant);
            mimageButtonFavourite.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_favorite_black_24dp));
        }

    }

    @OnClick(R.id.img_btn_direction)
    public void getDirectionToTheRestaurant() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + mRestaurant.getLocation().getLatitude() + "," + mRestaurant.getLocation().getLongitude()));
        startActivity(intent);
    }

    @OnClick(R.id.img_btn_zomato)
    public void openZomato() {
        launchUrl(mContext, mRestaurant.getMenuUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.restaurant_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.share) {

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, mRestaurant.getUrl());
            sharingIntent.setType("text/plain");
            startActivity(Intent.createChooser(sharingIntent, "Share This Restaurant with your Friend"));

        }
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateAllWidgets() {
//      Intent i = new Intent(mContext, WidgetProvider.class);
//        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        mContext.sendBroadcast(i);


        ////Second


        Context context = getApplicationContext();
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetCollectionList);
    }

    @Override
    public void showErrorView(int TYPE) {
        mProgressBarReviewLoader.setVisibility(View.GONE);
        switch (TYPE) {
            case Constants.ERROR_TYPE_NETWORK:
                addErrorLayout(Constants.ERROR_TYPE_NETWORK);
                break;
            case Constants.ERROR_TYPE_NO_DATA:
                addErrorLayout(Constants.ERROR_TYPE_NO_DATA);
                break;

            default:

                break;
        }
    }


    private void addErrorLayout(int errorType) {

        View errorView = getLayoutInflater().inflate(R.layout.no_data_found, null, false);
        TextView mTextViewMessage = (TextView) errorView.findViewById(R.id.tv_text);
        Button mButtonAction = (Button) errorView.findViewById(R.id.btn_action);
        ImageView mImageView = (ImageView) errorView.findViewById(R.id.iv_error_image);

        switch (errorType) {
            case Constants.ERROR_TYPE_NETWORK:
                mTextViewMessage.setText(getString(R.string.no_network_available));
                mButtonAction.setText(getString(R.string.retry_btn));
                mImageView.setImageResource(R.drawable.ic_cloud_off_black_24dp);
                mButtonAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLinearlayoutReviewContainer.removeAllViewsInLayout();
                        initApiCall();
                    }
                });


                break;
            case Constants.ERROR_TYPE_NO_DATA:
                mTextViewMessage.setText(getString(R.string.no_restaurant_found));
                mButtonAction.setVisibility(View.GONE);
                break;

            default:

                break;
        }

        mLinearlayoutReviewContainer.addView(errorView);


    }

    public void launchUrl(Context mContext, String url) {
        Logger.d(TAG, url);
        customTabsIntent.launchUrl((Activity) mContext, Uri.parse(url));

    }

}
