package com.iambedant.nanodegree.quickbite.data;

import com.iambedant.nanodegree.quickbite.data.local.PreferencesHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

//    private final RibotsService mRibotsService;
//    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
//    private final EventPosterHelper mEventPoster;

//    @Inject
//    public DataManager(RibotsService ribotsService, PreferencesHelper preferencesHelper,
//                       DatabaseHelper databaseHelper, EventPosterHelper eventPosterHelper) {
//        mRibotsService = ribotsService;
//        mPreferencesHelper = preferencesHelper;
//        mDatabaseHelper = databaseHelper;
//        mEventPoster = eventPosterHelper;
//    }

    @Inject
    public DataManager(PreferencesHelper preferencesHelper) {
        mPreferencesHelper = preferencesHelper;
    }



    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }



    /// Helper method to post events from doOnCompleted.
//    private Action0 postEventAction(final Object event) {
//        return new Action0() {
//            @Override
//            public void call() {
//                mEventPoster.postEventSafely(event);
//            }
//        };
//    }

}
