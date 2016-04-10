package com.iambedant.nanodegree.quickbite.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.Login.LoginActivity;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainMvpView {

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject
    MainPresenter mMainPresenter;
    //
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

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
        mMainPresenter.attachView(this);
        Intent intent = new Intent(this, LoginActivity.class);
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

}
