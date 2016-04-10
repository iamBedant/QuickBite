package com.iambedant.nanodegree.quickbite.data;

import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.iambedant.nanodegree.quickbite.data.local.PreferencesHelper;
import com.iambedant.nanodegree.quickbite.data.remote.FireBaseClient;
import com.iambedant.nanodegree.quickbite.util.EventPosterHelper;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.functions.Action0;

@Singleton
public class DataManager {

    //    private final RibotsService mRibotsService;
//    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final EventPosterHelper mEventPoster;
    private final FireBaseClient mFireBaseClient;

//    @Inject
//    public DataManager(RibotsService ribotsService, PreferencesHelper preferencesHelper,
//                       DatabaseHelper databaseHelper, EventPosterHelper eventPosterHelper) {
//        mRibotsService = ribotsService;
//        mPreferencesHelper = preferencesHelper;
//        mDatabaseHelper = databaseHelper;
//        mEventPoster = eventPosterHelper;
//    }

    @Inject
    public DataManager(PreferencesHelper preferencesHelper, EventPosterHelper eventPosterHelper, FireBaseClient fireBaseClient) {
        mPreferencesHelper = preferencesHelper;
        mEventPoster = eventPosterHelper;
        mFireBaseClient = fireBaseClient;
    }


    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

   /// Helper method to post events from doOnCompleted.
    private Action0 postEventAction(final Object event) {
        return new Action0() {
            @Override
            public void call() {
                mEventPoster.postEventSafely(event);
            }
        };
    }



    public Boolean createCustomUser(final String email, final String password){
        final Boolean status = false;
        mFireBaseClient.getmFireBaseRef().createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                loginUser(email,password);
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error

                Log.d("FireBase",firebaseError.toString());
            }
        });

        return status;
    }
    public void loginUser(String email, String password){
        mFireBaseClient.getmFireBaseRef().authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                Log.d("FireBase","SuccessfullyLoggedIn--->"+ authData.getUid());
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // there was an error
                Log.d("FireBase","error Logging in");
            }
        });
    }

}
