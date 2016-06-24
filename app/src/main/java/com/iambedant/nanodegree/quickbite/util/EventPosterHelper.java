package com.iambedant.nanodegree.quickbite.util;

import android.os.Handler;
import android.os.Looper;


import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

/**
 * Provides helper methods to post event to an Otto event bus
 */
public class EventPosterHelper {

    private final EventBus mBus;

    @Inject
    public EventPosterHelper(EventBus bus) {
        mBus = bus;
    }

    public EventBus getBus() {
        return mBus;
    }

    /**
     * Helper method to post an event from a different thread to the main one.
     */
    public void postEventSafely(final Object event) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mBus.post(event);
            }
        });
    }
}
