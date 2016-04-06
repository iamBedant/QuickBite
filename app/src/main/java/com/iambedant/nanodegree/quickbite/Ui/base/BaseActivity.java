package com.iambedant.nanodegree.quickbite.Ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iambedant.nanodegree.quickbite.injection.component.ActivityComponent;
import com.iambedant.nanodegree.quickbite.injection.component.DaggerActivityComponent;
import com.iambedant.nanodegree.quickbite.injection.module.ActivityModule;
import com.iambedant.nanodegree.quickbite.myApplication;


public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(myApplication.get(this).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

}
