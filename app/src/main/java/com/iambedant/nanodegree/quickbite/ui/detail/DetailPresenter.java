package com.iambedant.nanodegree.quickbite.ui.detail;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.data.model.Reviews.Reviews;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant_;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kuliza-193 on 4/15/2016.
 */
public class DetailPresenter extends BasePresenter<DetailMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;
    private final String TAG = DetailPresenter.class.getSimpleName();

    @Inject
    public DetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(DetailMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void saveRestaurant(Restaurant_ mRestaurant) {

        mDataManager.saveFavouriteRestaurant(mRestaurant);
        //TODO: Save It To LocalDB and firebase
    }

    public  void deleteRestaurant(String id) {
        mDataManager.deleteFavouriteRestaurant(id);
    }



    public void getReviews(String restaurantId){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(Constants.RESTAURANT_ID_KEY, restaurantId);
        params.put(Constants.REVIEW_COUNT_KEY, Constants.REVIEW_COUNT);

        Observable<Reviews> obj = mDataManager.getReviews(params);
        mSubscription = obj.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Reviews>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(TAG, "On Error Called->" + e.toString());
                    }

                    @Override
                    public void onNext(Reviews reviews) {
                        Logger.i(TAG,  "On Next Called");
                        getMvpView().showReviews(reviews.getUserReviews());
                    }
                });
    }

}
