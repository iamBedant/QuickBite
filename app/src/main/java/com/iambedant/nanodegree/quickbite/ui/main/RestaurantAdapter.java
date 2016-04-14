package com.iambedant.nanodegree.quickbite.ui.main;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant;
import com.iambedant.nanodegree.quickbite.ui.base.BaseAdapter;
import com.iambedant.nanodegree.quickbite.ui.views.RestaurantItemView;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Kuliza-193 on 4/11/2016.
 */
public class RestaurantAdapter extends BaseAdapter<Restaurant, RestaurantItemView> {
    private static final boolean DEFAULT_ANIMATION_ENABLED = true;
    private String TAG = RestaurantAdapter.class.getSimpleName();
    private boolean animationEnabled = DEFAULT_ANIMATION_ENABLED;
    protected List<Restaurant> mRestaurantList = new ArrayList<>();
    private MainPresenter mMainPresenter;

    public void enableAnimation() {
        animationEnabled = true;
    }

    public void disableAnimation() {
        animationEnabled = false;
    }

    public RestaurantAdapter(Context context) {
        super(context);
    }

    @Inject
    public RestaurantAdapter(Activity activity, MainPresenter presenter) {
        super(activity);
        this.mMainPresenter = presenter;
    }

//    @Inject
//    public RestaurantAdapter(Activity activity, MainPresenter presenter){
//
//    }

    @Override
    protected RestaurantItemView createView(Context context, ViewGroup viewGroup, int viewType) {
        return (RestaurantItemView) LayoutInflater.from(context)
                .inflate(R.layout.item_recycler_view_restaurant, viewGroup, false);
    }

    @Override
    protected void bind(final Restaurant restaurant,final RestaurantItemView view, ViewHolder<RestaurantItemView> holder) {
        if (restaurant != null) {
            view.setName(restaurant.getRestaurant().getName());
            view.setCuisine(restaurant.getRestaurant().getCuisines());
            view.setRating(restaurant.getRestaurant().getUserRating().getAggregateRating()+"");
            String imagePath = restaurant.getRestaurant().getThumb();
            Glide.with(context).load(restaurant.getRestaurant().getFeaturedImage())
                    .into(view.getLessonImageView());
            if (animationEnabled) {
                view.restartAnimation();
            }

            view.setOnRestaurantClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRestaurantClicked(restaurant, view);
                }
            });
        }
    }

    public void onRestaurantClicked(Restaurant restaurant, RestaurantItemView restaurantItemView) {
        Logger.d(TAG,"Clicked On "+ restaurant.getRestaurant().getName());
    }
}
