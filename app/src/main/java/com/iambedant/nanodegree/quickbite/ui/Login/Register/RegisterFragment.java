package com.iambedant.nanodegree.quickbite.ui.Login.Register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.User;
import com.iambedant.nanodegree.quickbite.ui.base.BaseFragment;
import com.iambedant.nanodegree.quickbite.ui.home.Home;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    private DatabaseReference mDatabase;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mContext = getActivity();
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
                // mRegisterFragmentPresenter.createCustomUser(mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString());
                createAccount(mEditTextEmail.getText().toString().trim(), mEditTextPassword.getText().toString().trim());

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

    private void createAccount(String email, String password) {

        if (!validateForm(email, password)) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(mContext, "Sign Up Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END create_user_with_email]
    }


    private void onAuthSuccess(FirebaseUser user) {
        String username = mEditTextName.getText().toString().trim();
        writeNewUser(user.getUid(), username, user.getEmail());
        startActivity(new Intent(mContext, Home.class));
        ((Activity) mContext).finish();
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

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabase.child("users").child(userId).setValue(user);
    }
}


