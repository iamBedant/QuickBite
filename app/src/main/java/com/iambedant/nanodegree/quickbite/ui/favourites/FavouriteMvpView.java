package com.iambedant.nanodegree.quickbite.ui.favourites;

import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant;
import com.iambedant.nanodegree.quickbite.ui.base.MvpView;

import java.util.List;

/**
 * Created by Kuliza-193 on 5/14/2016.
 */
public interface FavouriteMvpView extends MvpView {

   void showRestaurants(List<Restaurant> mRestaurantList);

}