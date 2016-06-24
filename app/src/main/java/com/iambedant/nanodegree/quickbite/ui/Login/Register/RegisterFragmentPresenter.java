package com.iambedant.nanodegree.quickbite.ui.Login.Register;

import android.text.TextUtils;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Constants;

import javax.inject.Inject;

/**
 * Created by Kuliza-193 on 4/9/2016.
 */
public class RegisterFragmentPresenter extends BasePresenter<RegisterFragmentMvpView> {
    private final DataManager mDataManager;
    String TAG = RegisterFragment.class.getSimpleName();

    @Inject
    public RegisterFragmentPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(RegisterFragmentMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }


    private boolean validateForm(String email, String password) {
        boolean valid = true;


        if (TextUtils.isEmpty(email)) {
            getMvpView().setError(0, "Required");
            valid = false;
        } else {

        }


        if (TextUtils.isEmpty(password)) {
            getMvpView().setError(0, "Required");
            valid = false;
        } else {

        }

        return valid;
    }

    public void createAccount(String email, String password, String name) {

        if (!validateForm(email, password)) {
            return;
        }
        mDataManager.createFirebaseUser(email, password, name);

    }

    public void writeNewUser(String uid, String displayName, String email) {
        mDataManager.writeNewUser(uid, displayName, email, Constants.LOGIN);
    }
}

