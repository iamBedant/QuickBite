package com.iambedant.nanodegree.quickbite.ui.searchCuisines;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisine_;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.util.AnimUtils;
import com.iambedant.nanodegree.quickbite.util.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kuliza-193 on 6/1/2016.
 */

public class CuisineSearch extends BaseActivity implements CuisineSearchMvpView {
    @Inject
    CuisineSearchPresenter mCuisineSearchPresenter;

    CuisinesAdapter mAdapter;

    @Bind(R.id.search_bar)
    RelativeLayout mRelativeLayoutSearchBar;

    @Bind(R.id.root_layout)
    FrameLayout mFrameLayoutRoot;

    @Bind(R.id.progress)
    ProgressBar mProgressBarLoading;

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Bind(R.id.back)
    ImageView mImageViewBack;

    @Bind(R.id.searchview)
    EditText mSearchView;
    Context mContext;
    int[] loc = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        loc = getIntent().getIntArrayExtra(Constants.SEARCH_POSITION);
        mContext = this;
        mCuisineSearchPresenter.attachView(this);
        initRecyclerView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            callCircularReveal();
        }else {
            mFrameLayoutRoot.setVisibility(View.VISIBLE);
        }

        mSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String queryString = mSearchView.getText().toString().trim();

                mCuisineSearchPresenter.getCuisines(queryString + "%");

            }
        });

        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ((Activity) mContext).setResult(Activity.RESULT_CANCELED, intent);
                ((Activity) mContext).finish();
            }
        });

    }

    public void callCircularReveal() {
        mFrameLayoutRoot.setVisibility(View.INVISIBLE);

        ViewTreeObserver viewTreeObserver = mFrameLayoutRoot.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    circularRevealActivity();
                    mFrameLayoutRoot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            });
        }
    }


    private void circularRevealActivity() {

        int cx = loc[0];
        int cy = loc[1];

        float finalRadius = Math.max(mFrameLayoutRoot.getWidth(), mFrameLayoutRoot.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal;


        circularReveal = ViewAnimationUtils.createCircularReveal(mFrameLayoutRoot, cx, cy, 0, finalRadius);


        circularReveal.setInterpolator(AnimUtils.getFastOutLinearInInterpolator(CuisineSearch
                .this));
        circularReveal.setDuration(1000);

        // make the view visible and start the animation
        mFrameLayoutRoot.setVisibility(View.VISIBLE);
        circularReveal.start();
    }

    private void reveal() {

    }

    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mAdapter = new CuisinesAdapter(mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void displayCuisines(List<Cuisine_> cuisine) {
        mAdapter.clearItems();
        mProgressBarLoading.setVisibility(View.GONE);
        mAdapter.setItems(cuisine);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCuisineSearchPresenter.detachView();
    }
}


