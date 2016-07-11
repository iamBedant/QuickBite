package com.iambedant.nanodegree.quickbite.util.customChrome;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by Kuliza-193 on 3/8/2016.
 */
public class KeepAliveService extends Service {
    private static final Binder sBinder = new Binder();


    @Override
    public IBinder onBind(Intent intent) {
        return sBinder;
    }
}