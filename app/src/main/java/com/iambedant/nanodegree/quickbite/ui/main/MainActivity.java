package com.iambedant.nanodegree.quickbite.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.Login.LoginActivity;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView, NavigationView.OnNavigationItemSelectedListener {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    MainPresenter mMainPresenter;
    //
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    View mHeaderView;
    Context mContext;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        mMainPresenter.attachView(this);
        setUpToolbar();
        mMainPresenter.updateNavHeader();

        Intent intent = new Intent(mContext, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    /*****
     * MVP View methods implementation
     *****/


    @Override
    public void showError() {
//        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_ribots))
//                .show();
    }

    @Override
    public void showRibotsEmpty() {
//        mRibotsAdapter.setRibots(Collections.<Ribot>emptyList());
//        mRibotsAdapter.notifyDataSetChanged();
        ///  Toast.makeText(this, R.string.empty_ribots, Toast.LENGTH_LONG).show();
    }

    private void setUpToolbar() {

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        setUpNavDrawer();
    }

    private void setUpNavDrawer() {
        if (mToolbar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mHeaderView = mNavigationView.inflateHeaderView(R.layout.nav_header);
        mNavigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void setUpNavHeader(String userName, String email) {
        TextView mTextViewUsername = (TextView) mHeaderView.findViewById(R.id.username);
        mTextViewUsername.setText(userName);
        TextView mTextViewEmail = (TextView) mHeaderView.findViewById(R.id.email);
        mTextViewEmail.setText(email);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.coffee) {
            // Handle the camera action
        } else if (id == R.id.lunch) {

        } else if (id == R.id.beer) {

        } else if (id == R.id.favourites) {

        } else if (id == R.id.account) {

        } else if (id == R.id.logout) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

}
