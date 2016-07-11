package com.iambedant.nanodegree.quickbite.ui.base;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.injection.component.ActivityComponent;
import com.iambedant.nanodegree.quickbite.injection.component.DaggerActivityComponent;
import com.iambedant.nanodegree.quickbite.injection.module.ActivityModule;
import com.iambedant.nanodegree.quickbite.myApplication;
import com.iambedant.nanodegree.quickbite.util.Logger;
import com.iambedant.nanodegree.quickbite.util.customChrome.CustomTabsHelper;

public class BaseActivity extends AppCompatActivity {
    public String TAG = BaseActivity.class.getSimpleName();
    private ActivityComponent mActivityComponent;
    private ProgressDialog mProgressDialog;

    CustomTabsClient mClient;
    CustomTabsSession mCustomTabsSession;


    private static class NavigationCallback extends CustomTabsCallback {
        @Override
        public void onNavigationEvent(int navigationEvent, Bundle extras) {
            Log.w("TAG", "onNavigationEvent: Code = " + navigationEvent);
        }
    }

    public CustomTabsIntent customTabsIntent;

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

    public void initChromeCustomTab() {
        Logger.d(TAG,"Custom Chrome tAb");
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder(getSession());
        builder.setToolbarColor(ContextCompat.getColor(this,R.color.colorPrimary )).setShowTitle(true);
        // prepareMenuItems(builder);
        builder.setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back_black));
        //  prepareActionButton(builder);
        builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right);

        customTabsIntent = builder.build();
        CustomTabsHelper.addKeepAliveExtra(this, customTabsIntent.intent);
    }

    private CustomTabsSession getSession() {
        if (mClient == null) {
            mCustomTabsSession = null;
        } else if (mCustomTabsSession == null) {
            mCustomTabsSession = mClient.newSession(new NavigationCallback());
        }
        return mCustomTabsSession;
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
