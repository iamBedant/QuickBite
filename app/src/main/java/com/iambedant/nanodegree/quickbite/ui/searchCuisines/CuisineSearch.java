package com.iambedant.nanodegree.quickbite.ui.searchCuisines;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.ProgressBar;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisine_;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kuliza-193 on 6/1/2016.
 */

public class CuisineSearch extends BaseActivity implements CuisineSearchMvpView {
    @Inject
    CuisineSearchPresenter mCuisineSearchPresenter;

    CuisinesAdapter mAdapter;

    @Bind(R.id.progressbar)
    ProgressBar mProgressBarLoading;

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Bind(R.id.searchview)
    SearchView mSearchView;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mContext = this;
        mCuisineSearchPresenter.attachView(this);
        initRecyclerView();
        mCuisineSearchPresenter.getCuisines();
    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mAdapter = new CuisinesAdapter(mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void displayCuisines(List<Cuisine_> cuisine) {
        mAdapter.setItems(cuisine);
    }
}


