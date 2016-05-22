package com.iambedant.nanodegree.quickbite.ui.list;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.SearchResult;
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
 * Created by Kuliza-193 on 5/14/2016.
 */
public class ListPresenter extends BasePresenter<ListMvpView> {

    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public ListPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ListMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }


    public void loadInitialData(int SELECTION_TYPE) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put(Constants.LAT_KEY, mDataManager.getLat());
        params.put(Constants.LON_KEY,mDataManager.getLon());
        switch (SELECTION_TYPE) {
            case Constants.TYPE_DINNER:
                params.put(Constants.CATEGORY_KEY, "2");
                break;
            case Constants.TYPE_TAKE_AWAY:
                params.put(Constants.CATEGORY_KEY, "5");
                break;
            case Constants.TYPE_BREAKFAST:
                params.put(Constants.CATEGORY_KEY, "8");
                break;
            case Constants.TYPE_COFFEE:
                params.put(Constants.CATEGORY_KEY, "6");
                break;
            case Constants.TYPE_BAR:
                params.put(Constants.CATEGORY_KEY, "3");
                break;
        }


        Observable<SearchResult> obj = mDataManager.getSearchData(params);
        mSubscription = obj.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("API_TEST", "On Error Called->" + e.toString());
                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        Logger.d("API_TEST", searchResult.getResultsStart() + "");
                        getMvpView().showRestaurants(searchResult.getRestaurants());
                    }
                });




    }

}
