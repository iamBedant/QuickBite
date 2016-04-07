package com.iambedant.nanodegree.quickbite.injection.module;

import android.app.Application;
import android.content.Context;

import com.iambedant.nanodegree.quickbite.injection.ApplicationContext;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Bus provideEventBus() {
        return new Bus();
    }

//    @Provides
//    @Singleton
//    RibotsService provideRibotsService() {
//        return RibotsService.Creator.newRibotsService();
//    }

}
