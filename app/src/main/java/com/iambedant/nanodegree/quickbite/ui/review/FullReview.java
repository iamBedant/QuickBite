package com.iambedant.nanodegree.quickbite.ui.review;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.list.ListPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class FullReview extends BaseActivity implements ReviewMvpView {

    @Inject
    ReviewPrsenter mReviewPresenter;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_review);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        mContext = this;
        mReviewPresenter.attachView(this);
    }

}
