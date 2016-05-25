package com.iambedant.nanodegree.quickbite.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListActivity extends BaseActivity implements ListMvpView {
    @Inject
    ListPresenter mListPresenter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    Boolean isLoadingComplete = false;

    @Bind(R.id.progressbar)
    ProgressBar mProgressBar;

    @Bind(R.id.error_view)
    View mViewError;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Context mContext;
    ListAdapter mListAdapter;
    int SELECTION_TYPE = 0;


    //TODO: Implement Infinite Loading;
    //TODO: Use Filter using bottom navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mContext = this;
        mListPresenter.attachView(this);
        setUpToolbar();
        initRecyclerView();

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                SELECTION_TYPE = getIntent().getExtras().getInt(Constants.TYPE_EXTRA_KEY);
            }
        }


        if (savedInstanceState == null) {
            initApiCall();
        } else {

            //TODO: get the filter and CurrentItem;

            if (savedInstanceState.getBoolean(Constants.BUNDLE_IS_DATA_LOADED)) {
                ArrayList<Restaurant> mRestaurants = savedInstanceState.getParcelableArrayList(Constants.BUNDLE_LIST_RESTAURANTS);
                showRestaurants(mRestaurants);
            } else {
                initApiCall();
            }
        }


    }


    public void initApiCall() {
        if (NetworkUtil.isNetworkConnected(mContext)) {
            mListPresenter.loadInitialData(SELECTION_TYPE);
        } else {
            //TODO: Show " No Network But you can Still Access your Favourite Restaurants"
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //TODO: Save Current Filter and Current Item;
        outState.putBoolean(Constants.BUNDLE_IS_DATA_LOADED, isLoadingComplete);
        if (isLoadingComplete) {
            outState.putParcelableArrayList(Constants.BUNDLE_LIST_RESTAURANTS, (ArrayList<? extends Parcelable>) mListAdapter.getItems());
        }
    }


    private void setUpToolbar() {

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    private void initRecyclerView() {
        mListAdapter = new ListAdapter(this);
        LinearLayoutManager mManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setAdapter(mListAdapter);
    }


    @Override
    public void showRestaurants(List<Restaurant> mRestaurantList) {
        isLoadingComplete = true;
        mListAdapter.setItems(mRestaurantList);
    }

    @Override
    public void controlLoading(Boolean isLoading) {

        if (isLoading) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showErrorView(int TYPE) {
        switch (TYPE) {
            case Constants.ERROR_TYPE_NETWORK:

                //TODO: Attach a animated vector drawable
                // mViewError.setBackground(R.drawable.);
                break;
            case Constants.ERROR_TYPE_NO_DATA:

                break;

            default:

                break;
        }
    }
}
