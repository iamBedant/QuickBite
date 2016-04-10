package com.iambedant.nanodegree.quickbite.data.remote;

import android.content.Context;

import com.firebase.client.Firebase;
import com.iambedant.nanodegree.quickbite.injection.ApplicationContext;
import com.iambedant.nanodegree.quickbite.util.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Kuliza-193 on 4/10/2016.
 */
@Singleton
public class FireBaseClient {
    Firebase mFireBaseRef;

    @Inject
    public FireBaseClient(@ApplicationContext Context context) {
        mFireBaseRef = new Firebase(Constants.FIREBASE_URL);
    }

    public Firebase getmFireBaseRef() {
        return mFireBaseRef;
    }

}
