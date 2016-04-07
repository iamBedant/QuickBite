package com.iambedant.nanodegree.quickbite.Data;

import javax.inject.Singleton;


@Singleton
public class DataManager {

//
//    private final PreferencesHelper mPreferencesHelper;
//    private final EventPosterHelper mEventPoster;
//
//    @Inject
//    public DataManager(RibotsService ribotsService, PreferencesHelper preferencesHelper,
//                       DatabaseHelper databaseHelper, EventPosterHelper eventPosterHelper) {
//        mRibotsService = ribotsService;
//        mPreferencesHelper = preferencesHelper;
//        mDatabaseHelper = databaseHelper;
//        mEventPoster = eventPosterHelper;
//    }
//
//    public PreferencesHelper getPreferencesHelper() {
//        return mPreferencesHelper;
//    }
//
//    public Observable<Ribot> syncRibots() {
//        return mRibotsService.getRibots()
//                .concatMap(new Func1<List<Ribot>, Observable<Ribot>>() {
//                    @Override
//                    public Observable<Ribot> call(List<Ribot> ribots) {
//                        return mDatabaseHelper.setRibots(ribots);
//                    }
//                });
//    }
//
//    public Observable<List<Ribot>> getRibots() {
//        return mDatabaseHelper.getRibots().distinct();
//    }
//
//
//    /// Helper method to post events from doOnCompleted.
//    private Action0 postEventAction(final Object event) {
//        return new Action0() {
//            @Override
//            public void call() {
//                mEventPoster.postEventSafely(event);
//            }
//        };
//    }

}
