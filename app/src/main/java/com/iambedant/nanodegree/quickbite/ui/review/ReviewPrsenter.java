package com.iambedant.nanodegree.quickbite.ui.review;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Logger;

import javax.inject.Inject;

/**
 * Created by Kuliza-193 on 5/31/2016.
 */

public class ReviewPrsenter extends BasePresenter<ReviewMvpView> {

    String TAG = ReviewPrsenter.class.getSimpleName();
    private final DataManager mDataManager;

    @Override
    public void attachView(ReviewMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
       // if (mSubscription != null) mSubscription.unsubscribe();
    }


    @Inject
    public ReviewPrsenter(DataManager dataManager) {
        Logger.d(TAG, "Presenter Created");
        mDataManager = dataManager;
    }

}
