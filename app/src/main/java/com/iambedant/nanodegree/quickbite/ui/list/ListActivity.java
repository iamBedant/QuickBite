package com.iambedant.nanodegree.quickbite.ui.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.searchCuisines.CuisineSearch;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.Logger;
import com.iambedant.nanodegree.quickbite.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListActivity extends BaseActivity implements ListMvpView {
    final String TAG = ListActivity.class.getSimpleName();

    @Inject
    ListPresenter mListPresenter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    Boolean isLoadingComplete = false;

    @Bind(R.id.root)
    CoordinatorLayout mRootLayout;

    @Bind(R.id.progressbar)
    ProgressBar mProgressBar;

    @Bind(R.id.error_view)
    View mViewError;

    @Bind(R.id.search)
    ImageButton mSearchButton;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;


    Context mContext;
    ListAdapter mListAdapter;
    int SELECTION_TYPE = 0;
    String SELECTED_CUISINE = "";

    private final int SEARCH_REQUEST_CODE = 1;


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
                mProgressBar.setVisibility(View.GONE);
                showRestaurants(mRestaurants);
            } else {
                SELECTED_CUISINE = savedInstanceState.getString(Constants.BUNDLE_SELECTED_CUISINE);
                SELECTION_TYPE = savedInstanceState.getInt(Constants.BUNDLE_SELECTION_TYPE);
                initApiCall();
            }
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //TODO: Save Current Filter and Current Item;
        outState.putBoolean(Constants.BUNDLE_IS_DATA_LOADED, isLoadingComplete);
        if (isLoadingComplete) {
            outState.putParcelableArrayList(Constants.BUNDLE_LIST_RESTAURANTS, (ArrayList<? extends Parcelable>) mListAdapter.getItems());
        } else {
            outState.putString(Constants.BUNDLE_SELECTED_CUISINE, SELECTED_CUISINE);
            outState.putInt(Constants.BUNDLE_SELECTION_TYPE, SELECTION_TYPE);

        }
    }

    @OnClick(R.id.search)
    public void openSearch() {

        Intent intent = new Intent(getApplicationContext(), CuisineSearch.class);
        int[] loc = new int[2];
        mSearchButton.getLocationOnScreen(loc);

        int[] locAbs = new int[2];

        locAbs[0] = loc[0] + (mSearchButton.getHeight() / 2);
        locAbs[1] = loc[1];

        intent.putExtra(Constants.SEARCH_POSITION, locAbs);
        startActivityForResult(intent, SEARCH_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SEARCH_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    mListAdapter.clearItems();
                    isLoadingComplete = false;
                    if (NetworkUtil.isNetworkConnected(mContext)) {
                        Logger.d(TAG, "ALL OK Calling API--->" + data.getStringExtra(Constants.SEARCH_TERM) );
                        mListPresenter.loadInitialData(SELECTION_TYPE, data.getStringExtra(Constants.SEARCH_TERM));
                    } else {
                        //TODO: Show " No Network But you can Still Access your Favourite Restaurants"
                    }

                }
                break;
        }
    }


    public void initApiCall() {
        if (NetworkUtil.isNetworkConnected(mContext)) {
            mListPresenter.loadInitialData(SELECTION_TYPE, SELECTED_CUISINE);
        } else {
            //TODO: Show " No Network But you can Still Access your Favourite Restaurants"
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListPresenter.detachView();
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
        mRecyclerView.setAdapter(mListAdapter);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                /* emulating https://material-design.storage.googleapis.com/publish/material_v_4/material_ext_publish/0B6Okdz75tqQsck9lUkgxNVZza1U/style_imagery_integration_scale1.png */
//                switch (position % 6) {
//                    case 5:
//                        return 3;
//                    case 3:
//                        return 2;
//                    default:
//                        return 1;
//                }
                return 3;
            }
        });
        mRecyclerView.setHasFixedSize(true);


//        LinearLayoutManager mManager = new LinearLayoutManager(mContext);
//        mRecyclerView.setLayoutManager(mManager);

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
