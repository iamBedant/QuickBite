package com.iambedant.nanodegree.quickbite.ui.SplashScreen;

import android.content.ContentValues;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.data.local.persistent.DataContract;
import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisine;
import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisines;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

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
    private final String TAG = SplashPresenter.class.getSimpleName();

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

    public Boolean isLoggedIn(){
        Boolean isLoggedIn = false;
        if(mDataManager.getCurrentUser()!=null){
            isLoggedIn = true;
        }
        return isLoggedIn;
    }

    public void loadCuisineslData(Double lat, Double lon) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("lat", lat+"");
        params.put("lon", lon+"");
        Observable<Cuisines> obj = mDataManager.getNearbyCuisines(params);
        mSubscription = obj.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Cuisines>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        //TODO: If network error Implement a Retry with  Exponential Backoff.
                        getMvpView().ShowErrorDialog();
                    }

                    @Override
                    public void onNext(Cuisines searchResult) {
                        Logger.d(TAG, searchResult.getCuisines().size() + "");
                        saveCuisinesToDb(searchResult.getCuisines());

                    }
                });
    }

    public void saveCuisinesToDb(List<Cuisine> cuisines) {


        Vector<ContentValues> cVVector = new Vector<ContentValues>(cuisines.size());
        for (int i = 0; i < cuisines.size(); i++) {
            Cuisine currentCuisine = cuisines.get(i);
            ContentValues cuisinesValues = new ContentValues();

            cuisinesValues.put(DataContract.CuisinesEntry.COLUMN_CUISINE_NAME, currentCuisine.getCuisine().getCuisineName());
            cuisinesValues.put(DataContract.CuisinesEntry.COLUMN_CUISINE_ID, currentCuisine.getCuisine().getCuisineId());
            cuisinesValues.put(DataContract.CuisinesEntry.COLUMN_IS_FAVOURITE, 0);
            cVVector.add(cuisinesValues);
        }

        mDataManager.saveCusinesToDb(cVVector);
        getMvpView().gotoManinScreen();
    }

    public void saveLocation(Double lat, Double lon, String locality){
        mDataManager.saveCurrentLocation(lat,lon,locality);
    }


}
