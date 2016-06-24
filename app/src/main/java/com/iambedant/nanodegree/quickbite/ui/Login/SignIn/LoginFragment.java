package com.iambedant.nanodegree.quickbite.ui.Login.SignIn;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseFragment;
import com.iambedant.nanodegree.quickbite.util.Logger;
import com.iambedant.nanodegree.quickbite.util.NetworkUtil;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.FacebookSdk.getApplicationContext;

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
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;

    @Inject
    LoginFragmentPresenter mLoginFragmentPresenter;

    public static String TAG = LoginFragment.class.getSimpleName();

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mContext = getActivity();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Logger.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Logger.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            mLoginFragmentPresenter.writeNewUser(task.getResult().getUser().getUid(), task.getResult().getUser().getDisplayName(), task.getResult().getUser().getEmail());
                        }

                    }
                });
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
                if(NetworkUtil.isNetworkConnected(mContext)) {
                    mLoginFragmentPresenter.signIn(mEditTextEmail.getText().toString().trim(), mEditTextPassword.getText().toString().trim());
                }else {
                    //todo: Show Network error
                }
            }
        });
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Logger.d(TAG, "facebook:onSuccess:" + loginResult);
                        handleFacebookAccessToken(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(mContext, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        return view;
    }



    @OnClick(R.id.btn_facebook)
    public void facebookClicked() {
        if(NetworkUtil.isNetworkConnected(mContext)) {
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        }else {
            //todo: use utility to show
        }
    }

    @OnClick(R.id.btn_google)
    public void googleClicked() {
        if(NetworkUtil.isNetworkConnected(mContext)) {
         //todo: Google Login to be implemented
        }else {
            //todo: use utility to show
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
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
}
