package com.iambedant.nanodegree.quickbite.ui.picklocation;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationActivity extends BaseActivity implements LocationMvpView {


    @Bind(R.id.back)
    ImageView mImageViewBack;
    @Bind(R.id.searchview)
    EditText mEditTextSearch;
    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    LocationPresenter mLoctionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocation);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
    }


}
