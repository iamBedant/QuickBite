package com.iambedant.nanodegree.quickbite.ui.Login.Register;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseFragment;
import com.iambedant.nanodegree.quickbite.util.Logger;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Kuliza-193 on 4/7/2016.
 */
public class RegisterFragment extends BaseFragment implements RegisterFragmentMvpView {

    @Bind(R.id.email)
    EditText mEditTextEmail;

    @Bind(R.id.password)
    EditText mEditTextPassword;

    @Bind(R.id.signup)
    Button mButtonSignUp;

    @Bind(R.id.tv_name)
    EditText mEditTextName;

    private FirebaseAuth mAuth;
    Context mContext;

    public static String TAG = RegisterFragment.class.getSimpleName();
    @Inject
    RegisterFragmentPresenter mRegisterFragmentPresenter;


    private CallbackManager mCallbackManager;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mContext = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_signup, null, false);
        getmFragmentComponent().inject(this);
        mRegisterFragmentPresenter.attachView(this);
        ButterKnife.bind(this, view);


        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRegisterFragmentPresenter.createAccount(mEditTextEmail.getText().toString().trim(), mEditTextPassword.getText().toString().trim(), mEditTextName.getText().toString().trim());

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


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            mRegisterFragmentPresenter.writeNewUser(task.getResult().getUser().getUid(), task.getResult().getUser().getDisplayName(), task.getResult().getUser().getEmail());


                        }

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRegisterFragmentPresenter.detachView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @OnClick(R.id.btn_google)
    public void googleClicked() {

    }

    @OnClick(R.id.btn_facebook)
    public void facebookClicked() {

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


