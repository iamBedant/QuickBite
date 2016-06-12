package com.iambedant.nanodegree.quickbite.ui.list;

import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant;
import com.iambedant.nanodegree.quickbite.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by Kuliza-193 on 5/14/2016.
 */
public interface ListMvpView extends MvpView {

   void showRestaurants(ArrayList<Restaurant> mRestaurantList);
   void controlLoading(Boolean isLoading);
   void showErrorView(int TYPE);
   void setUpToolbar(String title);

}