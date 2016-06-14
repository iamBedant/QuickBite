package com.iambedant.nanodegree.quickbite.injection.component;

import com.iambedant.nanodegree.quickbite.injection.PerActivity;
import com.iambedant.nanodegree.quickbite.injection.module.FragmentModule;
import com.iambedant.nanodegree.quickbite.ui.Login.Register.RegisterFragment;
import com.iambedant.nanodegree.quickbite.ui.Login.SignIn.LoginFragment;

import dagger.Component;

/**
 * Created by Kuliza-193 on 4/10/2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {
    void inject(RegisterFragment registerFragment);
    void inject(LoginFragment loginFragment);
}
