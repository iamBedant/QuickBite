package com.iambedant.nanodegree.quickbite.ui.Login.Register;

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

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    Context mContext;
    private OnFragmentInteractionListener mListener;

    public static String TAG = RegisterFragment.class.getSimpleName();
    @Inject
    RegisterFragmentPresenter mRegisterFragmentPresenter;



    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        return view;
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
        mListener.onSignInGoogleClecked();
    }

    @OnClick(R.id.btn_facebook)
    public void facebookClicked() {
        mListener.onSignInFbClicked();
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
    public void createFirebaseAccount(String email, String password, String name) {
        mListener.onSignUpClicked(email,password,name);
    }

    public interface OnFragmentInteractionListener {
        void onSignUpClicked(String email, String password, String name);

        void onSignInFbClicked();

        void onSignInGoogleClecked();

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


