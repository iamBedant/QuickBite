package com.iambedant.nanodegree.quickbite.ui.Login.SignIn;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseFragment;
import com.iambedant.nanodegree.quickbite.util.Logger;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    private FirebaseAuth.AuthStateListener mAuthListener;
    Context mContext;

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
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    //Save Name

                } else {
                    // User is signed out

                }
                // ...
            }
        };
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
                // mRegisterFragmentPresenter.createCustomUser(mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString());
                signIn(mEditTextEmail.getText().toString().trim(), mEditTextPassword.getText().toString().trim());

            }
        });
        return view;
    }


    private boolean validateForm(String email, String password) {
        boolean valid = true;


        if (TextUtils.isEmpty(email)) {
            mEditTextEmail.setError("Required.");
            valid = false;
        } else {
            mEditTextEmail.setError(null);
        }


        if (TextUtils.isEmpty(password)) {
            mEditTextPassword.setError("Required.");
            valid = false;
        } else {
            mEditTextPassword.setError(null);
        }

        return valid;
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm(email, password)) {
            return;
        }


//        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Logger.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "Authentication failed. "+  task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }
}
