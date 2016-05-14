package com.iambedant.nanodegree.quickbite.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.main.MainActivity;
import com.iambedant.nanodegree.quickbite.util.Constants;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends BaseActivity implements HomeMvpView {

    @Inject
    HomePresenter mHomePresenter;

    @Bind(R.id.tv_location)
    TextView mTextViewLocation;

    @Bind(R.id.tv_user_name)
    TextView mTextViewUserName;

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mContext = this;

        mHomePresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mHomePresenter.detachView();
    }

    @OnClick(R.id.rl_lunch)
    public void openLunchActivity() {

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(Constants.TYPE_EXTRA_KEY,Constants.TYPE_TAKE_AWAY);
        startActivity(intent);

    }

    @OnClick(R.id.rl_dinner)
    public void openDinnerActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(Constants.TYPE_EXTRA_KEY,Constants.TYPE_DINNER);
        startActivity(intent);

    }

    @OnClick(R.id.rl_breakfast)
    public void openBreakfastActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(Constants.TYPE_EXTRA_KEY,Constants.TYPE_BREAKFAST);
        startActivity(intent);

    }

    @OnClick(R.id.rl_bar)
    public void openBarActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(Constants.TYPE_EXTRA_KEY,Constants.TYPE_BAR);
        startActivity(intent);

    }

    @OnClick(R.id.rl_coffee)
    public void openCoffeeActivity() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(Constants.TYPE_EXTRA_KEY,Constants.TYPE_COFFEE);
        startActivity(intent);

    }

    @OnClick(R.id.rl_favourite)
    public void openFavouriteActivity() {

    }
}
