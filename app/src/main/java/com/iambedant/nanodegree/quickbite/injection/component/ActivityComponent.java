package com.iambedant.nanodegree.quickbite.injection.component;

import com.iambedant.nanodegree.quickbite.Ui.main.MainActivity;
import com.iambedant.nanodegree.quickbite.injection.PerActivity;
import com.iambedant.nanodegree.quickbite.injection.module.ActivityModule;

import dagger.Component;


/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
