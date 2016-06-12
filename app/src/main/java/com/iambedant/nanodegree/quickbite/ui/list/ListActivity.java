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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.searchCuisines.CuisineSearch;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.NetworkUtil;

import java.util.ArrayList;

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

    @Bind(R.id.tv_toolbar_title)
    TextView mTextViewToolBarTitle;

    @Bind(R.id.root)
    CoordinatorLayout mRootLayout;

    @Bind(R.id.progressbar)
    ProgressBar mProgressBar;

    @Bind(R.id.error_view)
    RelativeLayout mRelativeLayoutError;

    @Bind(R.id.search)
    ImageButton mSearchButton;

    @Bind(R.id.imb_btn_clear_filter)
    ImageButton mImageButtonClearFilter;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;


    Context mContext;
    ListAdapter mListAdapter;
    int SELECTION_TYPE = 0;
    String SELECTED_CUISINE = "";
    Boolean isCuisineFilterApplied = false;

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
        initRecyclerView();

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                SELECTION_TYPE = getIntent().getExtras().getInt(Constants.TYPE_EXTRA_KEY);
            }
        }


        mListPresenter.setUpToolBar(SELECTION_TYPE);

        if (savedInstanceState == null) {
            initApiCall();
        } else {
            isCuisineFilterApplied = savedInstanceState.getBoolean(Constants.BUNDLE_IS_CUISINE_FILTER_APPLIED);
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
        outState.putBoolean(Constants.BUNDLE_IS_CUISINE_FILTER_APPLIED, isCuisineFilterApplied);
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
                    isCuisineFilterApplied = true;
                    mListAdapter.clearItems();
                    isLoadingComplete = false;
                    SELECTED_CUISINE = data.getStringExtra(Constants.SEARCH_TERM);
                    initApiCall();
                }
                break;
        }
    }


    public void initApiCall() {
        isLoadingComplete = false;
        if (NetworkUtil.isNetworkConnected(mContext)) {
            mListPresenter.loadInitialData(SELECTION_TYPE, SELECTED_CUISINE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            showErrorView(Constants.ERROR_TYPE_NETWORK);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mListPresenter.detachView();
    }

    @Override
    public void setUpToolbar(String title) {

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mTextViewToolBarTitle.setText(title);

            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }

    private void initRecyclerView() {
        mListAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mListAdapter);
        GridLayoutManager gridLayoutManager = (GridLayoutManager) mRecyclerView.getLayoutManager();
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

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


    }


    @Override
    public void showRestaurants(ArrayList<Restaurant> mRestaurantList) {
        isLoadingComplete = true;
        if (mRestaurantList.size() > 0) {
            mListAdapter.setItems(mRestaurantList);
        } else {

            //Still need to set the items Otherwise it will crash during Screen Rotation
            mListAdapter.setItems(mRestaurantList);
            showErrorView(Constants.ERROR_TYPE_NO_DATA);
        }

        if (isCuisineFilterApplied) {
            mImageButtonClearFilter.setVisibility(View.VISIBLE);
            mImageButtonClearFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListAdapter.clearItems();
                    SELECTED_CUISINE = "";
                    isCuisineFilterApplied = false;
                    mRelativeLayoutError.removeAllViews();
                    mImageButtonClearFilter.setVisibility(View.GONE);
                    initApiCall();
                }
            });
        }

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
                addErrorLayout(Constants.ERROR_TYPE_NETWORK);
                break;
            case Constants.ERROR_TYPE_NO_DATA:
                addErrorLayout(Constants.ERROR_TYPE_NO_DATA);
                break;

            default:

                break;
        }
    }


    private void addErrorLayout(int errorType) {

        View noReviewView = getLayoutInflater().inflate(R.layout.no_data_found, null, false);
        TextView mTextViewMessage = (TextView) noReviewView.findViewById(R.id.tv_text);
        Button mButtonAction = (Button) noReviewView.findViewById(R.id.btn_action);
        ImageView mImageView = (ImageView) noReviewView.findViewById(R.id.iv_error_image);

        switch (errorType) {
            case Constants.ERROR_TYPE_NETWORK:
                mTextViewMessage.setText(getString(R.string.no_network_available));
                mButtonAction.setText(getString(R.string.retry_btn));
                mImageView.setImageResource(R.drawable.ic_cloud_off_black_24dp);
                mButtonAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initApiCall();
                    }
                });


                break;
            case Constants.ERROR_TYPE_NO_DATA:
                mTextViewMessage.setText(getString(R.string.no_restaurant_found));
                mButtonAction.setVisibility(View.GONE);
                break;

            default:

                break;
        }


        if (isCuisineFilterApplied) {

        }

        mRelativeLayoutError.addView(noReviewView);


    }

}
