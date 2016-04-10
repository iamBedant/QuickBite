package com.iambedant.nanodegree.quickbite.injection.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.iambedant.nanodegree.quickbite.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kuliza-193 on 4/10/2016.
 */
@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment provideActivity() {
        return mFragment;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mFragment.getActivity();
    }
}
