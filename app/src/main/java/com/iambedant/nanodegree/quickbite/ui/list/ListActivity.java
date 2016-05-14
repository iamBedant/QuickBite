package com.iambedant.nanodegree.quickbite.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.NetworkUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListActivity extends BaseActivity implements ListMvpView {
    @Inject
    ListPresenter mListPresenter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Context mContext;

    ListAdapter mListAdapter;

    int SELECTION_TYPE=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mContext = this;
        mListPresenter.attachView(this);
        setUpToolbar();
        initRecyclerView();

        if(getIntent()!=null){
            if(getIntent().getExtras()!=null){
                SELECTION_TYPE = getIntent().getExtras().getInt(Constants.TYPE_EXTRA_KEY);
            }
        }


        if(savedInstanceState==null){
            if (NetworkUtil.isNetworkConnected(mContext)) {
                mListPresenter.loadInitialData(SELECTION_TYPE);
            } else {
                //TODO: Show " No Network But you can Still Access your Favourite Restaurants"
            }
        }


    }


    private void setUpToolbar() {

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
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
        mListAdapter.setItems(mRestaurantList);
    }
}
