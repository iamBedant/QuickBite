package com.iambedant.nanodegree.quickbite.ui.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.main.MainActivity;
import com.iambedant.nanodegree.quickbite.util.NetworkUtil;

import javax.inject.Inject;

public class SplashScreen extends BaseActivity implements SplashMvpView {
    private static int SPLASH_TIME_OUT;
    Context mContext;
    @Inject
    SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getActivityComponent().inject(this);
        SPLASH_TIME_OUT = 2000;
        mContext = this;
        mSplashPresenter.attachView(this);

        if (NetworkUtil.isNetworkConnected(mContext)) {
            mSplashPresenter.loadCuisineslData();
        } else {
            //TODO: Show " No Network But you can Still Access your Favourite Restaurants"
        }

//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                Intent intent = new Intent(mContext,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, SPLASH_TIME_OUT);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSplashPresenter.detachView();
    }

    @Override
    public void gotoManinScreen() {

//        Logger.d("Bulk Insert before", contentValues.length+"");
//        int insertCount = mContext.getContentResolver().bulkInsert(DataContract.CuisinesEntry.CONTENT_URI, contentValues);
//        Logger.d("Bulk Insert after", insertCount + "");


//        if ( cVVector.size() > 0 ) {
//            Logger.d("Bulk Insert before", cVVector.size()+"");
//            ContentValues[] cvArray = new ContentValues[cVVector.size()];
//            cVVector.toArray(cvArray);
//          int count =  mContext.getContentResolver().bulkInsert(DataContract.CuisinesEntry.CONTENT_URI, cvArray);
//            Logger.d("Bulk Insert After", count+"");
//        }





        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
