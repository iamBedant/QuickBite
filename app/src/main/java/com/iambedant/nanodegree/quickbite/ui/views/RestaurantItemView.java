package com.iambedant.nanodegree.quickbite.ui.views;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iambedant.nanodegree.quickbite.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kuliza-193 on 4/11/2016.
 */
public class RestaurantItemView extends CardView {
    private View inflatedView;

    public AnimatorSet set;

    @Bind(R.id.name)
    TextView mTextViewName;

    @Bind(R.id.cuisine)
    TextView mTextViewCuisine;

    @Bind(R.id.image)
    ImageView mImageViewImage;

    @Bind(R.id.rating)
    TextView mTextViewRating;

    @Bind(R.id.address)
    TextView mTextViewAddress;

    @Bind(R.id.card)
    CardView mCardView;

    public RestaurantItemView(Context context) {
        super(context);
        initView();
    }

    public RestaurantItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RestaurantItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflatedView = inflater.inflate(R.layout.item_restaurant_view, this, true);
        ButterKnife.bind(this, inflatedView);
        set = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                R.animator.restaurant_animation);
        set.setTarget(this);
    }

//    public void setBookmarkButtonListener(OnClickListener listener) {
//        bookmarkIcon.setVisibility(VISIBLE);
//        bookmarkIcon.setOnClickListener(listener);
//    }
//
//    public void setRemoveIconListener(OnClickListener listener) {
//        removeIcon.setVisibility(VISIBLE);
//        removeIcon.setOnClickListener(listener);
//    }

//    public void setBookmarked(boolean bookmarked) {
//        if (bookmarked) {
//            bookmarkIcon.setColorFilter(Color.YELLOW);
//        } else {
//            bookmarkIcon.setColorFilter(Color.WHITE);
//        }
//    }

    public void setName(String name) {
        mTextViewName.setText(name);
    }

    public void setAddress(String address) {
        mTextViewAddress.setText(address);
    }
    public void setRating(String rating) {
        mTextViewRating.setText(rating);
    }
    public void setCuisine(String cuisine) {
        mTextViewCuisine.setText(cuisine);
    }
    public void setImage(Bitmap bitmap) {
        mImageViewImage.setImageBitmap(bitmap);
    }

    public void setOnRestaurantClickListener(OnClickListener listener) {
        mCardView.setOnClickListener(listener);
    }

    public ImageView getLessonImageView() {
        return mImageViewImage;
    }

    public void restartAnimation() {
        set.cancel();
        set.start();
    }

}
