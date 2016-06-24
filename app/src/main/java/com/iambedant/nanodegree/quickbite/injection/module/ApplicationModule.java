package com.iambedant.nanodegree.quickbite.injection.module;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iambedant.nanodegree.quickbite.data.remote.QuickBiteAPIClient;
import com.iambedant.nanodegree.quickbite.injection.ApplicationContext;

import org.greenrobot.eventbus.EventBus;

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
    EventBus provideEventBus() {
        return new EventBus();
    }

    @Provides
    @Singleton
    QuickBiteAPIClient provideQuickBiteAPIClient() {
        return QuickBiteAPIClient.Creator.newQuickBiteAPIClient();
    }

    @Provides
    @Singleton
    DatabaseReference databaseReference(){
        return FirebaseDatabase.getInstance().getReference();
    }

    @Provides
    @Singleton
    FirebaseAuth firebaseAuth(){
        return  FirebaseAuth.getInstance();
    }

//    @Provides
//    @Singleton
//    FirebaseDatabase provideFireBaseDataBase(){
//        return  FirebaseDatabase.getInstance();
//    }
//

}
