package com.iambedant.nanodegree.quickbite.ui.main;

import android.util.Log;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.SearchResult;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Constants;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void updateNavHeader() {
        getMvpView().setUpNavHeader(mDataManager.getPreferencesHelper()
                        .getString(Constants.USER_NAME, "hello"),
                mDataManager.getPreferencesHelper()
                        .getString(Constants.USER_EMAIL, "hi"));
    }


    public void loadInitialData() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("q", "whitefield");
        Observable<SearchResult> obj = mDataManager.getSearchData(params);
        mSubscription = obj.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("API_TEST", "On Error Called->" + e.toString());
                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        Log.d("API_TEST", searchResult.getResultsStart() + "");
                    }
                });
    }
}
