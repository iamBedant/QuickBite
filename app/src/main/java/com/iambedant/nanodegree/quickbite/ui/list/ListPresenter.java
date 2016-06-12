package com.iambedant.nanodegree.quickbite.ui.list;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.SearchResult;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.util.HashMap;

import javax.inject.Inject;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kuliza-193 on 5/14/2016.
 */
public class ListPresenter extends BasePresenter<ListMvpView> {

    String TAG = ListPresenter.class.getSimpleName();

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


    public void loadInitialData(int SELECTION_TYPE, String cuisineId) {

        getMvpView().controlLoading(true);
        HashMap<String, String> params = new HashMap<String, String>();

        params.put(Constants.LAT_KEY, mDataManager.getLat());
        params.put(Constants.LON_KEY, mDataManager.getLon());

        if (cuisineId != null && !cuisineId.isEmpty()) {
            params.put(Constants.CUISINE_ID, cuisineId);
        }

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
                        getMvpView().controlLoading(false);

                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            Response response = httpException.response();
                            getMvpView().showErrorView(Constants.ERROR_TYPE_DEFAULT);
                        } else {
                            getMvpView().showErrorView(Constants.ERROR_TYPE_NETWORK);
                        }

                    }

                    @Override
                    public void onNext(SearchResult searchResult) {
                        Logger.i(TAG, "Search successfull");
                        getMvpView().controlLoading(false);

                        getMvpView().showRestaurants(searchResult.getRestaurants());


                    }
                });


    }

    public void setUpToolBar(int SELECTION_TYPE) {

        String title;

        switch (SELECTION_TYPE) {
            case Constants.TYPE_DINNER:
                title = "DINNER";
                break;
            case Constants.TYPE_TAKE_AWAY:
                title = "TAKE AWAY";
                break;
            case Constants.TYPE_BREAKFAST:
                title = "BREAKFAST";
                break;
            case Constants.TYPE_COFFEE:
                title = "COFFEE";
                break;
            case Constants.TYPE_BAR:
                title = "BAR";
                break;
            default:
                title = "";
                break;
        }

        getMvpView().setUpToolbar(title);
    }
}
