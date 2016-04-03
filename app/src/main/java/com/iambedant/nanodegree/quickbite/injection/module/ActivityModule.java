package com.iambedant.nanodegree.quickbite.injection.module;

import android.app.Activity;
import android.content.Context;

import com.iambedant.nanodegree.quickbite.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }
}
