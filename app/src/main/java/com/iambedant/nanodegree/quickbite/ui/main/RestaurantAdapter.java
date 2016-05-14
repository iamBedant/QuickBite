package com.iambedant.nanodegree.quickbite.ui.main;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant;
import com.iambedant.nanodegree.quickbite.ui.base.BaseAdapter;
import com.iambedant.nanodegree.quickbite.ui.detail.DetailActivity;
import com.iambedant.nanodegree.quickbite.ui.views.RestaurantItemView;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kuliza-193 on 4/11/2016.
 */
public class RestaurantAdapter extends BaseAdapter<Restaurant, RestaurantItemView> {
    private static final boolean DEFAULT_ANIMATION_ENABLED = true;
    private String TAG = RestaurantAdapter.class.getSimpleName();
    private boolean animationEnabled = DEFAULT_ANIMATION_ENABLED;
    protected List<Restaurant> mRestaurantList = new ArrayList<>();
    Activity host;
  public void enableAnimation() {
        animationEnabled = true;
    }

    public void disableAnimation() {
        animationEnabled = false;
    }

    public RestaurantAdapter(Context context) {
        super(context);
    }

    public RestaurantAdapter(Activity activity) {
        super(activity);
        this.host = activity;
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
    protected void bind(final Restaurant restaurant, final RestaurantItemView view, final ViewHolder<RestaurantItemView> holder) {
        if (restaurant != null) {
            view.setName(restaurant.getRestaurant().getName());
            view.setCuisine(restaurant.getRestaurant().getCuisines());
            view.setRating(restaurant.getRestaurant().getUserRating().getAggregateRating() + "");
            view.setAddress(restaurant.getRestaurant().getLocation().getAddress());
            Glide.with(host)
                    .load(restaurant.getRestaurant().getFeaturedImage())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .dontTransform()
                    .into(view.getLessonImageView());
            if (animationEnabled) {
                view.restartAnimation();
            }
            view.setOnRestaurantClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.d("Test_click", "Inside Click Listenr");
                    Intent intent = new Intent(host, DetailActivity.class);
                    intent.putExtra(Constants.CURRENT_RESTAURANT,restaurant.getRestaurant());

                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options =
                                ActivityOptions.makeSceneTransitionAnimation(host,
                                        Pair.create(v, host.getString(R.string.restaurant_cover_transition)),
                                        Pair.create(v, host.getString(R.string
                                                .transition_background)));
                        host.startActivity(intent, options.toBundle());

                    } else {
                        host.startActivity(intent);
                    }
                }
            });
        }
    }

}
