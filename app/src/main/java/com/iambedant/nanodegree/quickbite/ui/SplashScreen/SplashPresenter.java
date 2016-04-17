package com.iambedant.nanodegree.quickbite.ui.SplashScreen;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisine;
import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisines;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kuliza-193 on 4/17/2016.
 */
public class SplashPresenter extends BasePresenter<SplashMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;

    @Inject
    public SplashPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(SplashMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void loadCuisineslData() {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("lat", "12.9539974");
        params.put("lon", "77.6309395");
        Observable<Cuisines> obj = mDataManager.getNearbyCuisines(params);
        mSubscription = obj.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Cuisines>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("API_TEST", "On Error Called->" + e.toString());
                    }

                    @Override
                    public void onNext(Cuisines searchResult) {
                        Logger.d("API_TEST", "Cusines Result");
                        Logger.d("API_TEST", searchResult.getCuisines() + "");
                        saveCuisinesToDb(searchResult.getCuisines());

                    }
                });
    }

    public void saveCuisinesToDb(List<Cuisine> cuisines){

   //    TODO// Save Data To DB
        getMvpView().gotoManinScreen();
    }
}
