package com.iambedant.nanodegree.quickbite.ui.Login;

import com.iambedant.nanodegree.quickbite.data.DataManager;
import com.iambedant.nanodegree.quickbite.ui.base.BasePresenter;
import com.iambedant.nanodegree.quickbite.util.Constants;

import javax.inject.Inject;

/**
 * Created by Kuliza-193 on 4/7/2016.
 */
public class LoginPresenter extends BasePresenter<LoginMvpView> {

    private final DataManager mDataManager;

    @Inject
    public LoginPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(LoginMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();

    }

    public void writeNewUser(String uid, String username, String email) {
        mDataManager.writeNewUser(uid, username, email, Constants.LOGIN);
    }

    public void createFirebaseAccount(String email, String password, String name) {
        mDataManager.createFirebaseUser(email, password, name);
    }

    public void signInToFirebase(String email, String password) {
        mDataManager.firebaseLogin(email, password);
    }
}
