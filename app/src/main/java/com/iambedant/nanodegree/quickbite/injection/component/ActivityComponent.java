package com.iambedant.nanodegree.quickbite.injection.component;

import com.iambedant.nanodegree.quickbite.injection.PerActivity;
import com.iambedant.nanodegree.quickbite.injection.module.ActivityModule;
import com.iambedant.nanodegree.quickbite.ui.Login.LoginActivity;
import com.iambedant.nanodegree.quickbite.ui.Login.Register.RegisterFragment;
import com.iambedant.nanodegree.quickbite.ui.main.MainActivity;

import dagger.Component;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);



}
