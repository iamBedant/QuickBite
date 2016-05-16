package com.iambedant.nanodegree.quickbite.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant_;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.util.Constants;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity implements DetailMvpView, View.OnClickListener {


    @Bind(R.id.scrim)
    ImageView mScrimView;

    @Bind(R.id.container)
    ImageView mImageViewCover;

    @Bind(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    Restaurant_ mRestaurant;


    @Inject
    DetailPresenter mDetailPresenter;
    Context mContext;

    private static final float SCRIM_ADJUSTMENT = 0.075f;
    CollapsingToolbarLayout collapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            postponeEnterTransition();
//        }

        ButterKnife.bind(this);

        getActivityComponent().inject(this);
        mContext = this;
        mDetailPresenter.attachView(this);
        final Intent intent = getIntent();
        if (intent.hasExtra(Constants.CURRENT_RESTAURANT)) {
            mRestaurant = getIntent().getExtras().getParcelable(Constants.CURRENT_RESTAURANT);
            loadSharedElement();
        }


        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("My Name");
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
                    Log.d("Alpha", Math.round(f) + "");
                } else {
                    mScrimView.getBackground().setAlpha(0);
                }
            }
        });

    }

    private void loadSharedElement() {
        mScrimView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            Glide.with(this)
                    .load(mRestaurant.getFeaturedImage())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .priority(Priority.IMMEDIATE)
                    .into(mImageViewCover);


        } else {
            Glide.with(this)
                    .load(mRestaurant.getFeaturedImage())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .priority(Priority.IMMEDIATE)
                   // .listener(shotLoadListener)
//                    .listener(new RequestListener<String, GlideDrawable>() {
//                        @Override
//                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            startPostponedEnterTransition();
//                            return false;
//                        }
//                    })
                    .into(mImageViewCover);

        }

    }

    private static int statusBarHeight(android.content.res.Resources res) {
        return (int) (24 * res.getDisplayMetrics().density);
    }

    @Override
    public void onBackPressed() {
        supportFinishAfterTransition();

    }

//    private RequestListener shotLoadListener = new RequestListener<String, GlideDrawable>() {
//        @Override
//        public boolean onResourceReady(GlideDrawable resource, String model,
//                                       Target<GlideDrawable> target, boolean isFromMemoryCache,
//                                       boolean isFirstResource) {
//            final Bitmap bitmap = GlideUtils.getBitmap(resource);
//            final int twentyFourDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//                    24, mContext.getResources().getDisplayMetrics());
//            Palette.from(bitmap)
//                    .maximumColorCount(3)
//                    .clearFilters() /* by default palette ignore certain hues
//                        (e.g. pure black/white) but we don't want this. */
//                    .setRegion(0, 0, bitmap.getWidth() - 1, twentyFourDip) /* - 1 to work around
//                        https://code.google.com/p/android/issues/detail?id=191013 */
//                    .generate(new Palette.PaletteAsyncListener() {
//                        @Override
//                        public void onGenerated(Palette palette) {
//                            boolean isDark;
//                            @ColorUtils.Lightness int lightness = ColorUtils.isDark(palette);
//                            if (lightness == ColorUtils.LIGHTNESS_UNKNOWN) {
//                                isDark = ColorUtils.isDark(bitmap, bitmap.getWidth() / 2, 0);
//                            } else {
//                                isDark = lightness == ColorUtils.IS_DARK;
//                            }
//
////                            if (!isDark) { // make back icon dark on light images
////                                back.setColorFilter(ContextCompat.getColor(
////                                        mContext, R.color.dark_icon));
////                            }
//
//                            // color the status bar. Set a complementary dark color on L,
//                            // light or dark color on M (with matching status bar icons)
//                            int statusBarColor = getWindow().getStatusBarColor();
//                            final Palette.Swatch topColor =
//                                    ColorUtils.getMostPopulousSwatch(palette);
//                            if (topColor != null &&
//                                    (isDark || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
//                                statusBarColor = ColorUtils.scrimify(topColor.getRgb(),
//                                        isDark, SCRIM_ADJUSTMENT);
//                                // set a light status bar on M+
//                                if (!isDark && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                    ViewUtils.setLightStatusBar(mImageViewCover);
//                                }
//                            }
//
//                            if (statusBarColor != getWindow().getStatusBarColor()) {
//                                mScrimView.setBackgroundColor(statusBarColor);
////                                mImageViewCover.setScrimColor(statusBarColor);
//                                ValueAnimator statusBarColorAnim = ValueAnimator.ofArgb(
//                                        getWindow().getStatusBarColor(), statusBarColor);
//                                statusBarColorAnim.addUpdateListener(new ValueAnimator
//                                        .AnimatorUpdateListener() {
//                                    @Override
//                                    public void onAnimationUpdate(ValueAnimator animation) {
//                                        getWindow().setStatusBarColor(
//                                                (int) animation.getAnimatedValue());
//                                    }
//                                });
//                                statusBarColorAnim.setDuration(1000L);
//                                statusBarColorAnim.setInterpolator(
//                                        getFastOutSlowInInterpolator(DetailActivity.this));
//                                statusBarColorAnim.start();
//                            }
//                        }
//                    });
//
////            Palette.from(bitmap)
////                    .clearFilters()
////                    .generate(new Palette.PaletteAsyncListener() {
////                        @Override
////                        public void onGenerated(Palette palette) {
////                            // color the ripple on the image spacer (default is grey)
////                            shotSpacer.setBackground(ViewUtils.createRipple(palette, 0.25f, 0.5f,
////                                    ContextCompat.getColor(mContext, R.color.mid_grey),
////                                    true));
////                            // slightly more opaque ripple on the pinned image to compensate
////                            // for the scrim
////                            imageView.setForeground(ViewUtils.createRipple(palette, 0.3f, 0.6f,
////                                    ContextCompat.getColor(mContext, R.color.mid_grey),
////                                    true));
////                        }
////                    });
////
////            // TODO should keep the background if the image contains transparency?!
////            imageView.setBackground(null);
//            return false;
//        }
//
//        @Override
//        public boolean onException(Exception e, String model, Target<GlideDrawable> target,
//                                   boolean isFirstResource) {
//            return false;
//        }
//    };

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

//    public void startPostponedEnterTransition() {
//
//        mScrimView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                mScrimView.getViewTreeObserver().removeOnPreDrawListener(this);
//                startPostponedEnterTransition();
//                return true;
//            }
//        });
//
//    }
}
