package com.iambedant.nanodegree.quickbite.ui.Login.SignIn;

import android.text.TextUtils;
import android.util.Log;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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




    private boolean validateForm(String email, String password) {
        boolean valid = true;


        if (TextUtils.isEmpty(email)) {
            getMvpView().setError(0, "Required");

            valid = false;
        } else {
            if(!isValidEmail(email)){
                getMvpView().setError(0, "Required");
                valid = false;
            }
        }


        if (TextUtils.isEmpty(password)) {
            getMvpView().setError(1, "Required");
            valid = false;
        } else {

        }

        return valid;
    }

    public Boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        getMvpView().signInFirebase(email,password);

    }
}

