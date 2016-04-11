package com.iambedant.nanodegree.quickbite.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant;
import com.iambedant.nanodegree.quickbite.ui.base.BaseAdapter;
import com.iambedant.nanodegree.quickbite.ui.views.RestaurantItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuliza-193 on 4/11/2016.
 */
public class RestaurantAdapter extends BaseAdapter<Restaurant,RestaurantItemView> {
    private static final boolean DEFAULT_ANIMATION_ENABLED = true;

    private boolean animationEnabled = DEFAULT_ANIMATION_ENABLED;
    protected List<Restaurant> mRestaurantList = new ArrayList<>();

    public void enableAnimation() {
        animationEnabled = true;
    }

    public void disableAnimation() {
        animationEnabled = false;
    }



    public RestaurantAdapter(Context context) {
        super(context);
    }

    @Override
    protected RestaurantItemView createView(Context context, ViewGroup viewGroup, int viewType) {
        return (RestaurantItemView) LayoutInflater.from(context)
                .inflate(R.layout.item_restaurant_view, viewGroup, false);
    }

    @Override
    protected void bind(Restaurant restaurant, RestaurantItemView view, ViewHolder<RestaurantItemView> holder) {
//        if (restaurant != null){
//            view.setLessonName(restaurant.getRestaurant().getName());
//            String imagePath = get;
//            Picasso.with(context).load("file:///android_asset/" + imagePath)
//                    .into(view.getLessonImageView());
//            if (animationEnabled) {
//                view.restartAnimation();
//            }
//            view.setOnLessonClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onLessonClicked(lesson, view);
//                }
//            });
//        }
    }
}
