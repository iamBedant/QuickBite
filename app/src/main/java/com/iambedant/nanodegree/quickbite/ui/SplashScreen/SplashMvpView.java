package com.iambedant.nanodegree.quickbite.ui.SplashScreen;

import android.content.ContentValues;

import com.iambedant.nanodegree.quickbite.ui.base.MvpView;

import java.util.Vector;

/**
 * Created by Kuliza-193 on 4/17/2016.
 */
public interface SplashMvpView extends MvpView {
    public void gotoManinScreen(Vector<ContentValues> cVVector);
}
