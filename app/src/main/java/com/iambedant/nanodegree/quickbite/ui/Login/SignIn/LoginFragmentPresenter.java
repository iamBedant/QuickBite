package com.iambedant.nanodegree.quickbite.ui.Login.SignIn;

import android.text.TextUtils;
import android.util.Log;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Constants;

import javax.inject.Inject;

/**
 * Created by Kuliza-193 on 4/9/2016.
 */
public class LoginFragmentPresenter extends BasePresenter<LoginFragmentMvpView> {
    private final DataManager mDataManager;
    String TAG = LoginFragmentPresenter.class.getSimpleName();

    @Inject
    public LoginFragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(LoginFragmentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }


    public void writeNewUser(String uid, String username, String email) {
        mDataManager.writeNewUser(uid, username, email, Constants.LOGIN);
    }

    private boolean validateForm(String email, String password) {
        boolean valid = true;


        if (TextUtils.isEmpty(email)) {
            getMvpView().setError(0, "Required");

            valid = false;
        } else {

        }


        if (TextUtils.isEmpty(password)) {
            getMvpView().setError(1, "Required");
            valid = false;
        } else {

        }

        return valid;
    }

    public void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        mDataManager.firebaseLogin(email, password);

    }
}

