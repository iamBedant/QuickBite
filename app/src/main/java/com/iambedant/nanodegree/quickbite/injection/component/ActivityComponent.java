package com.iambedant.nanodegree.quickbite.injection.component;

import com.iambedant.nanodegree.quickbite.injection.PerActivity;
import com.iambedant.nanodegree.quickbite.injection.module.ActivityModule;
import com.iambedant.nanodegree.quickbite.ui.Login.LoginActivity;
import com.iambedant.nanodegree.quickbite.ui.SplashScreen.SplashScreen;
import com.iambedant.nanodegree.quickbite.ui.detail.DetailActivity;
import com.iambedant.nanodegree.quickbite.ui.favourites.Favourites;
import com.iambedant.nanodegree.quickbite.ui.home.Home;
import com.iambedant.nanodegree.quickbite.ui.list.ListActivity;
import com.iambedant.nanodegree.quickbite.ui.main.MainActivity;
import com.iambedant.nanodegree.quickbite.ui.restaurant.RestaurantActivity;
import com.iambedant.nanodegree.quickbite.ui.review.FullReview;
import com.iambedant.nanodegree.quickbite.ui.searchCuisines.CuisineSearch;


import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(DetailActivity detailActivity);

    void inject(SplashScreen splashScreen);

    void inject(Home home);

    void inject(ListActivity listActivity);

    void inject(Favourites favourites);

    void inject(FullReview fullReview);

    void inject(RestaurantActivity restaurantActivity);

    void inject(CuisineSearch cuisineSearch);

//    void inject(RestaurantAdapter restaurantAdapter, RestaurantAdapter.OnRestaurantClick onRestaurantClick);
//
//    RestaurantAdapter.OnRestaurantClick onRestaurantClick();


}
