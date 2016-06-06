package com.iambedant.nanodegree.quickbite.ui.favourites;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Favourites extends BaseActivity implements FavouriteMvpView, LoaderManager.LoaderCallbacks<Cursor> {
    @Inject
    FavouritePresenter mFavouritePresenter;
    private FavouriteAdapter mFavouriteAdapter;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    Context mContext;

    private static final int FAVOURITE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mContext = this;
        setUpToolbar();

        getLoaderManager().initLoader(FAVOURITE_LOADER, null, (android.app.LoaderManager.LoaderCallbacks<Cursor>) this);

        mFavouriteAdapter = new FavouriteAdapter(mContext);

    }

    private void setUpToolbar() {

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public void NavigateToDetailPage() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mFavouritePresenter.getFavouriteRestaurants();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mFavouriteAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mFavouriteAdapter.swapCursor(null);
    }

}
