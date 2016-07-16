package com.iambedant.nanodegree.quickbite.ui.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.events.EventLogout;
import com.iambedant.nanodegree.quickbite.events.EventNameSuccessfull;
import com.iambedant.nanodegree.quickbite.events.EventPasswordUpdate;
import com.iambedant.nanodegree.quickbite.ui.Login.LoginActivity;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.DialogFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Settings extends BaseActivity implements SettingsMvpView {


    @Inject
    SettingsPresenter mSettingsPresenter;
    Context mContext;
    @Bind(R.id.ll_name)
    LinearLayout mLinearLayoutName;
    @Bind(R.id.ll_pwd)
    LinearLayout mLinearLayoutPwd;
    @Bind(R.id.et_name)
    EditText mEditTextName;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.et_new_pwd)
    EditText mEditTextOldPwd;
    @Bind(R.id.et_old_pwd)
    EditText mEditTextNewPwd;
    @Bind(R.id.btn_update_name)
    Button mButtonUpdateName;
    @Bind(R.id.btn_update_pwd)
    Button mButtonPwd;
    ProgressDialog mProgressDialog;
    @Bind(R.id.tv_toolbar_title)
    TextView mToolbarTitle;
    @Bind(R.id.rl_change_password)
    RelativeLayout mRelativeLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mContext = this;
        mProgressDialog = DialogFactory.createProgressDialog(mContext, R.string.please_wait_);
        if (!(mSettingsPresenter.getProvider() == Constants.FIREBASE)) {
            mRelativeLayoutPassword.setVisibility(View.GONE);
        }
        setUpToolbar();
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.rl_change_name)
    public void changeName() {
        if (mLinearLayoutName.getVisibility() == View.VISIBLE) {
            mLinearLayoutName.setVisibility(View.GONE);
        } else {
            mLinearLayoutName.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.btn_update_pwd)
    public void updatePassword() {
        hideKeyBoard();
        mProgressDialog.show();
        mSettingsPresenter.updatePassword(mEditTextOldPwd.getText().toString(), mEditTextNewPwd.getText().toString());
    }

    @OnClick(R.id.btn_update_name)
    public void updateName() {
        hideKeyBoard();
        mProgressDialog.show();
        mSettingsPresenter.updateName(mEditTextName.getText().toString().trim());
    }


    @Subscribe
    public void onEvent(EventNameSuccessfull event) {
        mProgressDialog.dismiss();
        if (event.isSuccessfull) {
            changeName();
            showSnack(getString(R.string.name_updated));

        } else {
            showSnack(getString(R.string.something_went_wrong));
        }
    }

    @OnClick(R.id.rl_change_password)
    public void changePassword() {


        if (mLinearLayoutPwd.getVisibility() == View.VISIBLE) {
            mLinearLayoutPwd.setVisibility(View.GONE);
        } else {
            mLinearLayoutPwd.setVisibility(View.VISIBLE);
        }


    }

    @OnClick(R.id.rl_logout)
    public void logout() {
        mProgressDialog.show();

        mSettingsPresenter.logout();
    }


    @Subscribe
    public void onEvent(EventLogout event) {
        mProgressDialog.dismiss();
        if (event.isSuccessfull) {

            Intent intent = new Intent(mContext, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void showSnack(String message) {

        Snackbar.make(findViewById(R.id.htab_maincontent), message, Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.RED)
                .show();
    }

    @Subscribe
    public void onEvent(EventPasswordUpdate event) {
        hideKeyBoard();
        mProgressDialog.dismiss();
        if (event.isSuccessfull) {
            changePassword();
            showSnack(getString(R.string.password_updated));
        } else {
            showSnack(event.message);
        }
    }


    public void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setUpToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbarTitle.setText(R.string.settings);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
