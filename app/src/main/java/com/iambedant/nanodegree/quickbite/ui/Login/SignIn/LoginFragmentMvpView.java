package com.iambedant.nanodegree.quickbite.ui.Login.SignIn;

import com.iambedant.nanodegree.quickbite.ui.base.MvpView;

/**
 * Created by Kuliza-193 on 4/9/2016.
 */
public interface LoginFragmentMvpView extends MvpView {

    void setError(int i, String required);

    void signInFirebase(String email, String password);
}
