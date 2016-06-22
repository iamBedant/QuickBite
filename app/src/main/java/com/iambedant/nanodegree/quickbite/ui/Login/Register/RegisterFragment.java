package com.iambedant.nanodegree.quickbite.ui.Login.Register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.Favourite;
import com.iambedant.nanodegree.quickbite.data.model.User;
import com.iambedant.nanodegree.quickbite.ui.base.BaseFragment;
import com.iambedant.nanodegree.quickbite.ui.home.Home;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.util.HashMap;
import java.util.Map;

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

    private DatabaseReference mDatabase;
    private CallbackManager mCallbackManager;
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
                            onAuthSuccessFacebook(task.getResult().getUser());

                        }

                    }
                });
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




    private void onAuthSuccessFacebook(FirebaseUser user) {
        String username = user.getDisplayName();
        writeNewUserFacebook(user.getUid(), username, user.getEmail());
    }

    private void writeNewUserFacebook(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabase.child("users").child(userId).updateChildren(user.toMap());
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getFavouriteRestaurants(mAuth.getCurrentUser());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getFavouriteRestaurants(FirebaseUser user) {
        Logger.d(TAG, "getFavourite Restaurant Called");
        final Query mQuery = mDatabase.child("users").child(user.getUid()).child("favourites");


        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Logger.d(TAG, "ParsingHashMap");
                HashMap<String, HashMap<String, String>> mMap = (HashMap<String, HashMap<String, String>>) dataSnapshot.getValue();

                saveRestaurantsToLocalStorage(mMap);
                mQuery.removeEventListener(this);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //todo: After Saving Restaurants Navigate to Homepage

    }

    public void saveRestaurantsToLocalStorage(HashMap<String, HashMap<String, String>> mMap) {
        Logger.d(TAG, "Saving to local");
        for (Map.Entry<String, HashMap<String, String>> entry : mMap.entrySet()) {

            HashMap<String, String> favourite = entry.getValue();
            mRegisterFragmentPresenter.AddFavourites(new Favourite(favourite.get("restaurantId"),
                    favourite.get("restaurantName"),
                    favourite.get("coverImage"),
                    favourite.get("cuisine"),
                    favourite.get("address"),
                    favourite.get("lat"),
                    favourite.get("lon"),
                    favourite.get("rating"),
                    Integer.parseInt(String.valueOf(favourite.get("price")))
            ));
        }

        navigatiToHome();


    }

    public void navigatiToHome() {
        Logger.d(TAG, "Navigating to home");
        Intent intent = new Intent(mContext, Home.class);
        getActivity().startActivity(intent);
        getActivity().finish();
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

    @OnClick(R.id.btn_google)
    public void googleClicked() {

    }

    @OnClick(R.id.btn_facebook)
    public void facebookClicked(){

    }

}


