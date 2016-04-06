package com.iambedant.nanodegree.quickbite.Ui.main;

import com.iambedant.nanodegree.quickbite.Ui.base.BasePresenter;

import rx.Subscription;


public class MainPresenter extends BasePresenter<MainMvpView> {
    private Subscription mSubscription;
    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null) mSubscription.unsubscribe();

    }
    public void loadRibots() {
        checkViewAttached();
//        mSubscription = mDataManager.getRibots()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Subscriber<List<Ribot>>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Timber.e(e, "There was an error loading the ribots.");
//                        getMvpView().showError();
//                    }
//
//                    @Override
//                    public void onNext(List<Ribot> ribots) {
//                        if (ribots.isEmpty()) {
//                            getMvpView().showRibotsEmpty();
//                        } else {
//                            getMvpView().showRibots(ribots);
//                        }
//                    }
//                });
    }
}
