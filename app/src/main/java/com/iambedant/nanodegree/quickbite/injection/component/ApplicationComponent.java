package com.iambedant.nanodegree.quickbite.injection.component;

import android.app.Application;
import android.content.Context;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.data.local.PreferencesHelper;
import com.iambedant.nanodegree.quickbite.data.remote.FireBaseClient;
import com.iambedant.nanodegree.quickbite.injection.ApplicationContext;
import com.iambedant.nanodegree.quickbite.injection.module.ApplicationModule;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

   // void inject(SyncService syncService);

    @ApplicationContext
    Context context();
    Application application();
    FireBaseClient fireBaseClient();
   // RibotsService ribotsService();
    PreferencesHelper preferencesHelper();
  //  DatabaseHelper databaseHelper();
    DataManager dataManager();
    Bus eventBus();

}
