package com.iambedant.nanodegree.quickbite.ui.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iambedant.nanodegree.quickbite.ui.main.MainActivity;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SPLASH_TIME_OUT=2000;
        mContext = this;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(mContext,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
