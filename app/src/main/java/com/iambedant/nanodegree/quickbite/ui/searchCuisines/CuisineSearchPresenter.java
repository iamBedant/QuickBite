package com.iambedant.nanodegree.quickbite.ui.searchCuisines;

import android.database.Cursor;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.data.local.persistent.DataContract;
import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisine_;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Kuliza-193 on 6/1/2016.
 */

public class CuisineSearchPresenter extends BasePresenter<CuisineSearchMvpView> {

    String TAG = CuisineSearchPresenter.class.getSimpleName();

    private final DataManager mDataManager;

    @Inject
    public CuisineSearchPresenter(DataManager dataManager) {
        Logger.d(TAG, "Presenter Created");
        mDataManager = dataManager;
    }

    @Override
    public void attachView(CuisineSearchMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();

    }

    public void getCuisines() {

        Cursor mCursor = mDataManager.getAllCuisines();

        List<Cuisine_> mCuisinesList = new ArrayList<>();

        int columnIndexCuisine = mCursor.getColumnIndex(DataContract.CuisinesEntry.COLUMN_CUISINE_NAME);
        int columnIndexcuisineId = mCursor.getColumnIndex(DataContract.CuisinesEntry.COLUMN_CUISINE_ID);
        int columnIndexCuisineIsFavourite = mCursor.getColumnIndex(DataContract.CuisinesEntry.COLUMN_IS_FAVOURITE);

        while (mCursor.moveToNext()) {
            mCuisinesList.add(new Cuisine_(mCursor.getInt(columnIndexcuisineId), mCursor.getString(columnIndexCuisine)));
        }
        getMvpView().displayCuisines(mCuisinesList);
    }

}
