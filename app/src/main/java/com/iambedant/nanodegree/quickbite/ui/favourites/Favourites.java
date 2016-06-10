package com.iambedant.nanodegree.quickbite.ui.favourites;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.util.Logger;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Favourites extends BaseActivity implements FavouriteMvpView, LoaderManager.LoaderCallbacks<Cursor>, ClickCallBack {
    @Inject
    FavouritePresenter mFavouritePresenter;
    private FavouriteAdapter mFavouriteAdapter;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.progressbar)
    ProgressBar mProgressBar;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.search)
    ImageButton mSearchButton;
    Context mContext;

    @Bind(R.id.tv_toolbar_title)
    TextView mTextViewToolBarTitle;

    private final String TAG = Favourites.class.getSimpleName();

    private static final int FAVOURITE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mFavouritePresenter.attachView(this);
        mContext = this;
        setUpToolbar();
        getSupportLoaderManager().initLoader(FAVOURITE_LOADER, null, this);

        mFavouriteAdapter = new FavouriteAdapter(mContext, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mFavouriteAdapter);

    }

    private void setUpToolbar() {

        if (mToolbar != null) {

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            mTextViewToolBarTitle.setText("FAVOURITES");
            mSearchButton.setVisibility(View.GONE);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }

    @Override
    public void NavigateToDetailPage() {

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Logger.d(TAG, "Loader Created");
        return mFavouritePresenter.getFavouriteRestaurants();

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Logger.d(TAG, "Loader Finished");
        mProgressBar.setVisibility(View.GONE);
        mFavouriteAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Logger.d(TAG, "Loader Restarted");
        mFavouriteAdapter.swapCursor(null);
    }

    @Override
    public void favouriteIconClicked(String id) {
        mFavouritePresenter.deleteRestaurant(id);

    }

    @Override
    public void directionClicked(Double lon, Double lat) {
        //todo: Open Map  intent wth  Direction
    }

    @Override
    public void zomatoClicked(String id) {
        // TODO: Open Zomato Intent
    }
}
