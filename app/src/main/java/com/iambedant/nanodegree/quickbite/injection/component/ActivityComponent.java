package com.iambedant.nanodegree.quickbite.injection.component;

import android.app.Activity;

import com.iambedant.nanodegree.quickbite.injection.PerActivity;
import com.iambedant.nanodegree.quickbite.injection.module.ActivityModule;
import com.iambedant.nanodegree.quickbite.ui.Login.LoginActivity;
import com.iambedant.nanodegree.quickbite.ui.detail.DetailActivity;
import com.iambedant.nanodegree.quickbite.ui.main.MainActivity;
import com.iambedant.nanodegree.quickbite.ui.main.RestaurantAdapter;

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

    void inject(RestaurantAdapter restaurantAdapter);

    Activity activity();


}
