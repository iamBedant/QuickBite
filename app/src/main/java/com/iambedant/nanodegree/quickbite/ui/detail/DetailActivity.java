package com.iambedant.nanodegree.quickbite.ui.detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;

import javax.inject.Inject;

public class DetailActivity extends BaseActivity implements DetailMvpView {



    @Inject
    DetailPresenter mDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
