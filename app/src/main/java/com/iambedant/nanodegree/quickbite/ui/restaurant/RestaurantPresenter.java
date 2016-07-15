package com.iambedant.nanodegree.quickbite.ui.restaurant;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.data.model.Reviews.Reviews;
import com.iambedant.nanodegree.quickbite.data.model.SearchResult.Restaurant_;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.Logger;
import com.iambedant.nanodegree.quickbite.util.NetworkUtil;

import java.util.HashMap;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Kuliza-193 on 4/15/2016.
 */
public class RestaurantPresenter extends BasePresenter<RestaurantMvpView> {
    private final DataManager mDataManager;
    private Subscription mSubscription;
    private final String TAG = RestaurantPresenter.class.getSimpleName();
    private DatabaseReference mDatabase;

    @Inject
    public RestaurantPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(RestaurantMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();
    }

    public void saveRestaurant(final Restaurant_ mRestaurant) {

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Favourite user = dataSnapshot.getValue(Favourite.class);
                        if (userId == null) {

                            Logger.e(TAG, "User " + userId + " is unexpectedly null");

                        } else {
                            mDataManager.writeNewPost(userId, mRestaurant);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                    }
                });


    }





    public void deleteRestaurant(String id) {
        mDataManager.deleteRestaurantFromFirebase(id);
         getMvpView().updateAllWidgets();
    }


    public void getReviews(String restaurantId) {
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
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            if (NetworkUtil.isHttpStatusCode(e, 500)) {
                                //todo: add server error
                                getMvpView().showErrorView(Constants.ERROR_TYPE_DEFAULT);
                            } else if (NetworkUtil.isHttpStatusCode(e, 400)) {
                                //todo: add bad request error
                                getMvpView().showErrorView(Constants.ERROR_TYPE_DEFAULT);
                            }

                        } else {
                            getMvpView().showErrorView(Constants.ERROR_TYPE_NETWORK);
                        }
                    }

                    @Override
                    public void onNext(Reviews reviews) {
                        Logger.i(TAG, "On Next Called");
                        getMvpView().showReviews(reviews.getUserReviews());
                    }
                });
    }

    public boolean isRestaurantPresent(String id) {
        return mDataManager.isRestaurantPresent(id);
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

}
