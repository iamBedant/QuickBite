package com.iambedant.nanodegree.quickbite.ui.Login;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.events.EventLoginSuccessfull;
import com.iambedant.nanodegree.quickbite.ui.Login.Register.RegisterFragment;
import com.iambedant.nanodegree.quickbite.ui.Login.SignIn.LoginFragment;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.home.Home;
import com.iambedant.nanodegree.quickbite.util.DialogFactory;
import com.iambedant.nanodegree.quickbite.util.EventPosterHelper;
import com.iambedant.nanodegree.quickbite.util.Logger;
import com.iambedant.nanodegree.quickbite.util.NetworkUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements LoginMvpView, GoogleApiClient.OnConnectionFailedListener, LoginFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener {

    private String TAG = LoginActivity.class.getSimpleName();

    @Bind(R.id.tabs)
    TabLayout mTablayoutContainer;

    @Bind(R.id.viewpager)
    ViewPager mViewPagerContainer;

    @Bind(R.id.scrim)
    ImageView mImageViewScrim;

    @Bind(R.id.iv_tab_header_signin)
    ImageSwitcher mImageViewHeaderSignIn;
    //
    @Inject
    EventPosterHelper mEventPosterHelper;

    Context mContext;
    ProgressDialog mProgressBar;

    @Inject
    LoginPresenter mLoginPresenter;

    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mLoginPresenter.attachView(this);
        mContext = this;
        mProgressBar = DialogFactory.createProgressDialog(mContext, "Please Wait ...");
        FacebookSdk.sdkInitialize(getApplicationContext());
        EventBus.getDefault().register(this);
        mAuth = FirebaseAuth.getInstance();
        mImageViewHeaderSignIn.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView mImageView = new ImageView(getApplicationContext());
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return mImageView;
            }
        });


        mImageViewHeaderSignIn.setInAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));

        mImageViewHeaderSignIn.setOutAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_out));
        mImageViewHeaderSignIn.setImageResource(R.drawable.login_bg);
        setupViewPager(mViewPagerContainer);
        mTablayoutContainer.setupWithViewPager(mViewPagerContainer);

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


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(LoginActivity.this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


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
                            mLoginPresenter.writeNewUser(task.getResult().getUser().getUid(), task.getResult().getUser().getDisplayName(), task.getResult().getUser().getEmail());
                        } else {
                            mProgressBar.dismiss();
                            showSnack(task.getException().getMessage());
                        }

                    }
                });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "Sign In");
        adapter.addFragment(new RegisterFragment(), "Sign Up");
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {

                    final int composeColor = blendColors(ContextCompat.getColor(mContext, R.color.signin_scrim_color), ContextCompat.getColor(mContext, R.color.signup_scrim_color), positionOffset);
                    mImageViewScrim.setBackgroundColor(composeColor);


                } else {
                    final int composeColor = blendColors(ContextCompat.getColor(mContext, R.color.signup_scrim_color), ContextCompat.getColor(mContext, R.color.signin_scrim_color), positionOffset);
                    mImageViewScrim.setBackgroundColor(composeColor);
//                    if(positionOffset>=.5){
//                        mImageViewHeaderSignIn.setImageResource(R.drawable.login_bg);
//
//                    }
//                    else {

//                    }
                }


            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {

                    mImageViewHeaderSignIn.setImageResource(R.drawable.login_bg);
                } else {
                    mImageViewHeaderSignIn.setImageResource(R.drawable.signup_bg);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private static int blendColors(int color1, int color2, float ratio) {
        final float inverseRation = 1f - ratio;
        float r = (Color.red(color1) * ratio) + (Color.red(color2) * inverseRation);
        float g = (Color.green(color1) * ratio) + (Color.green(color2) * inverseRation);
        float b = (Color.blue(color1) * ratio) + (Color.blue(color2) * inverseRation);
        return Color.argb(150, (int) r, (int) g, (int) b);
    }

    @Override
    public void showDataEmpty() {

    }

    @Override
    public void showError() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(EventLoginSuccessfull event) {
        Logger.d(TAG, "This is inside the event Bus !!!");
        if (event.isSuccessfull) {
            mProgressBar.dismiss();
            Logger.d(TAG, "This is inside the event Bus !!!, Login SuccessFull");
            Intent intent = new Intent(mContext, Home.class);
            startActivity(intent);
            finish();
        } else {
            showSnack(event.message);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);


                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener((Activity) mContext, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Logger.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                                if (task.isSuccessful()) {
                                    mLoginPresenter.writeNewUser(task.getResult().getUser().getUid(), task.getResult().getUser().getDisplayName(), task.getResult().getUser().getEmail());
                                }

                            }
                        });


            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showSnack(getString(R.string.play_services_error));

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onSignInClicked(String email, String password) {
        if (NetworkUtil.isNetworkConnected(mContext)) {
            mProgressBar.show();
            mLoginPresenter.signInToFirebase(email, password);
        } else {
            showSnack(getString(R.string.network_not_available));
        }

    }

    @Override
    public void onSignUpClicked(String email, String password, String name) {
        if (NetworkUtil.isNetworkConnected(mContext)) {
            mProgressBar.show();
            mLoginPresenter.createFirebaseAccount(email, password, name);
        } else {
            showSnack(getString(R.string.network_not_available));
        }


    }

    @Override
    public void onSignInFbClicked() {
        if (NetworkUtil.isNetworkConnected(mContext)) {
            mProgressBar.show();
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        } else {
            showSnack(getString(R.string.network_not_available));
        }
    }

    @Override
    public void onSignInGoogleClecked() {

        if (NetworkUtil.isNetworkConnected(mContext)) {
            mProgressBar.show();
            signIn();
        } else {

        }
    }

    public void showSnack(String message) {
        Snackbar.make(findViewById(R.id.htab_maincontent), message, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();
    }
}
