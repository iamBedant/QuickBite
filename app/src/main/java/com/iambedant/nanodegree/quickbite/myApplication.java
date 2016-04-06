package com.iambedant.nanodegree.quickbite;

import android.app.Application;
import android.content.Context;

import com.iambedant.nanodegree.quickbite.injection.component.ApplicationComponent;
import com.iambedant.nanodegree.quickbite.injection.component.DaggerApplicationComponent;
import com.iambedant.nanodegree.quickbite.injection.module.ApplicationModule;


public class myApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

//        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
//            Fabric.with(this, new Crashlytics());
//        }
    }

    public static myApplication get(Context context) {
        return (myApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
