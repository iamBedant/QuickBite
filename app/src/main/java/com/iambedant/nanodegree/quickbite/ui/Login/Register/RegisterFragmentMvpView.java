package com.iambedant.nanodegree.quickbite.ui.Login.Register;

import com.iambedant.nanodegree.quickbite.ui.base.MvpView;

/**
 * Created by Kuliza-193 on 4/9/2016.
 */
public interface RegisterFragmentMvpView extends MvpView {

    void setError(int i, String required);

    void createFirebaseAccount(String email, String password, String name);
}
