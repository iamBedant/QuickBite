package com.iambedant.nanodegree.quickbite.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.injection.component.ActivityComponent;
import com.iambedant.nanodegree.quickbite.injection.component.DaggerActivityComponent;
import com.iambedant.nanodegree.quickbite.injection.module.ActivityModule;
import com.iambedant.nanodegree.quickbite.myApplication;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;
    private ProgressDialog mProgressDialog;
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



    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.please_wait));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }



    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgressDialog();
    }

}
