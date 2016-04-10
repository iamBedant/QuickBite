package com.iambedant.nanodegree.quickbite.ui.Login.SignIn;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iambedant.nanodegree.quickbite.R;

/**
 * Created by Kuliza-193 on 4/7/2016.
 */
public class LoginFragment extends Fragment implements LoginFragmentMvpView{
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signin, container, false);
    }
}
