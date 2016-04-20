package com.iambedant.nanodegree.quickbite.ui.detail;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.util.TypedValue;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.views.ParallaxScrimageView;
import com.iambedant.nanodegree.quickbite.util.ColorUtils;
import com.iambedant.nanodegree.quickbite.util.GlideUtils.GlideUtils;
import com.iambedant.nanodegree.quickbite.util.ViewUtils;

import javax.inject.Inject;

import static com.iambedant.nanodegree.quickbite.util.AnimUtils.getFastOutSlowInInterpolator;

public class DetailActivity extends BaseActivity implements DetailMvpView {



    @Inject
    DetailPresenter mDetailPresenter;
    Context mContext;
    ParallaxScrimageView img;
    private static final float SCRIM_ADJUSTMENT = 0.075f;
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mContext = this;
        String url = getIntent().getStringExtra("image");
        img = (ParallaxScrimageView) findViewById(R.id.container);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("My Name");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            Glide.with(this)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .priority(Priority.IMMEDIATE)
                    .into(img);
        }
        else {
            Glide.with(this)
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .priority(Priority.IMMEDIATE)
                    .listener(shotLoadListener)
                    .into(img);
        }



    }

    @Override
    public void onBackPressed() {
        expandImageAndFinish();

    }

    private RequestListener shotLoadListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onResourceReady(GlideDrawable resource, String model,
                                       Target<GlideDrawable> target, boolean isFromMemoryCache,
                                       boolean isFirstResource) {
            final Bitmap bitmap = GlideUtils.getBitmap(resource);
            final int twentyFourDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    24, mContext.getResources().getDisplayMetrics());
            Palette.from(bitmap)
                    .maximumColorCount(3)
                    .clearFilters() /* by default palette ignore certain hues
                        (e.g. pure black/white) but we don't want this. */
                    .setRegion(0, 0, bitmap.getWidth() - 1, twentyFourDip) /* - 1 to work around
                        https://code.google.com/p/android/issues/detail?id=191013 */
                    .generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            boolean isDark;
                            @ColorUtils.Lightness int lightness = ColorUtils.isDark(palette);
                            if (lightness == ColorUtils.LIGHTNESS_UNKNOWN) {
                                isDark = ColorUtils.isDark(bitmap, bitmap.getWidth() / 2, 0);
                            } else {
                                isDark = lightness == ColorUtils.IS_DARK;
                            }

//                            if (!isDark) { // make back icon dark on light images
//                                back.setColorFilter(ContextCompat.getColor(
//                                        mContext, R.color.dark_icon));
//                            }

                            // color the status bar. Set a complementary dark color on L,
                            // light or dark color on M (with matching status bar icons)
                            int statusBarColor = getWindow().getStatusBarColor();
                            final Palette.Swatch topColor =
                                    ColorUtils.getMostPopulousSwatch(palette);
                            if (topColor != null &&
                                    (isDark || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                                statusBarColor = ColorUtils.scrimify(topColor.getRgb(),
                                        isDark, SCRIM_ADJUSTMENT);
                                // set a light status bar on M+
                                if (!isDark && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    ViewUtils.setLightStatusBar(img);
                                }
                            }

                            if (statusBarColor != getWindow().getStatusBarColor()) {
//                                img.setScrimColor(statusBarColor);
                                ValueAnimator statusBarColorAnim = ValueAnimator.ofArgb(
                                        getWindow().getStatusBarColor(), statusBarColor);
                                statusBarColorAnim.addUpdateListener(new ValueAnimator
                                        .AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        getWindow().setStatusBarColor(
                                                (int) animation.getAnimatedValue());
                                    }
                                });
                                statusBarColorAnim.setDuration(1000L);
                                statusBarColorAnim.setInterpolator(
                                        getFastOutSlowInInterpolator(DetailActivity.this));
                                statusBarColorAnim.start();
                            }
                        }
                    });

//            Palette.from(bitmap)
//                    .clearFilters()
//                    .generate(new Palette.PaletteAsyncListener() {
//                        @Override
//                        public void onGenerated(Palette palette) {
//                            // color the ripple on the image spacer (default is grey)
//                            shotSpacer.setBackground(ViewUtils.createRipple(palette, 0.25f, 0.5f,
//                                    ContextCompat.getColor(mContext, R.color.mid_grey),
//                                    true));
//                            // slightly more opaque ripple on the pinned image to compensate
//                            // for the scrim
//                            imageView.setForeground(ViewUtils.createRipple(palette, 0.3f, 0.6f,
//                                    ContextCompat.getColor(mContext, R.color.mid_grey),
//                                    true));
//                        }
//                    });
//
//            // TODO should keep the background if the image contains transparency?!
//            imageView.setBackground(null);
            return false;
        }

        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                   boolean isFirstResource) {
            return false;
        }
    };

    private void expandImageAndFinish() {
        if (img.getOffset() != 0f) {
            Animator expandImage = ObjectAnimator.ofFloat(img, ParallaxScrimageView.OFFSET,
                    0f);
            expandImage.setDuration(80);
            expandImage.setInterpolator(getFastOutSlowInInterpolator(this));
            expandImage.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                        finishAfterTransition();
                    }
                    else {
                        supportFinishAfterTransition();
                    }

                }
            });
            expandImage.start();
        } else {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                finishAfterTransition();
            }
            else {
                supportFinishAfterTransition();
            }
        }
    }
}
