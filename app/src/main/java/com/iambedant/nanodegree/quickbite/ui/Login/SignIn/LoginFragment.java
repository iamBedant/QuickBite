package com.iambedant.nanodegree.quickbite.ui.Login.SignIn;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseFragment;
import com.iambedant.nanodegree.quickbite.util.NetworkUtil;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kuliza-193 on 4/7/2016.
 */
public class LoginFragment extends BaseFragment implements LoginFragmentMvpView {
    @Bind(R.id.tv_email)
    EditText mEditTextEmail;

    @Bind(R.id.tv_password)
    EditText mEditTextPassword;

    @Bind(R.id.btn_signin)
    Button mButtonSignIn;

    private FirebaseAuth mAuth;
    Context mContext;

    private OnFragmentInteractionListener mListener;

    @Inject
    LoginFragmentPresenter mLoginFragmentPresenter;

    public static String TAG = LoginFragment.class.getSimpleName();

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_signin, null, false);
        getmFragmentComponent().inject(this);
        mLoginFragmentPresenter.attachView(this);
        ButterKnife.bind(this, view);
        mButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtil.isNetworkConnected(mContext)) {
                    mLoginFragmentPresenter.signIn(mEditTextEmail.getText().toString().trim(), mEditTextPassword.getText().toString().trim());
                } else {
                    //todo: Show Network error on Snack Bar
                }
            }
        });
         return view;
    }


    @OnClick(R.id.btn_facebook)
    public void facebookClicked() {
        mListener.onSignInFbClicked();


    }

    @OnClick(R.id.btn_google)
    public void googleClicked() {
        mListener.onSignInGoogleClecked();

    }

   @Override
    public void setError(int i, String required) {
        switch (i) {
            case 0:
                //Show email Error
                break;
            case 1:
                //show password Error
                break;
        }
    }

    @Override
    public void signInFirebase(String email, String password) {
        mListener.onSignInClicked(email,password);
    }



    protected void onAttachToContext(Context context) {
        Activity activity = (Activity) context;
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onSignInClicked(String email, String password);

        void onSignInFbClicked();

        void onSignInGoogleClecked();

    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    /*
    * Deprecated on API 23
    * Use onAttachToContext instead
    */

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }
}
