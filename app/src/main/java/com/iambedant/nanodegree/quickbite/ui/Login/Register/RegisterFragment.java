package com.iambedant.nanodegree.quickbite.ui.Login.Register;

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

    @Inject
    RegisterFragmentPresenter mRegisterFragmentPresenter;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_signup, null, false);
        getmFragmentComponent().inject(this);
        mRegisterFragmentPresenter.attachView(this);
        ButterKnife.bind(this,view);
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mRegisterFragmentPresenter.createCustomUser(mEditTextEmail.getText().toString(), mEditTextPassword.getText().toString());
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mRegisterFragmentPresenter.detachView();
    }
}
