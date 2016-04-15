package com.iambedant.nanodegree.quickbite.ui.main;

import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant_;
import com.iambedant.nanodegree.quickbite.ui.base.MvpView;
import com.iambedant.nanodegree.quickbite.ui.views.RestaurantItemView;

import java.util.List;


public interface MainMvpView extends MvpView {


    void showRibotsEmpty();

    void showError();

    void setUpNavHeader(String userName, String email);

    void showRestaurants(List<Restaurant> mRestaurantList);
    void navigateToDetailActivity(RestaurantItemView restaurantItemView, Restaurant_ restaurant);

}
