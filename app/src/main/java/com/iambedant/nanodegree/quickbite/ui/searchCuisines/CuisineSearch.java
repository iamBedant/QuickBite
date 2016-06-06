package com.iambedant.nanodegree.quickbite.ui.searchCuisines;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
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
        mCuisineSearchPresenter.getCuisines();
        callCircularReveal();

    }

    public void callCircularReveal(){
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

//        int cx = rootLayout.getWidth() / 2;
//        int cy = rootLayout.getHeight() / 2;

        int cx = loc[0];
        int cy = loc[1];

        float finalRadius = Math.max(mFrameLayoutRoot.getWidth(), mFrameLayoutRoot.getHeight());

        // create the animator for this view (the start radius is zero)



        Animator circularReveal = ViewAnimationUtils.createCircularReveal(mFrameLayoutRoot, cx, cy, 0, finalRadius);
        circularReveal.setInterpolator(AnimUtils.getFastOutLinearInInterpolator(CuisineSearch
                .this));
        circularReveal.setDuration(1000);

        // make the view visible and start the animation
        mFrameLayoutRoot.setVisibility(View.VISIBLE);
        circularReveal.start();
    }


    private void initRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        mAdapter = new CuisinesAdapter(mContext);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void displayCuisines(List<Cuisine_> cuisine) {
        mProgressBarLoading.setVisibility(View.GONE);
        mAdapter.setItems(cuisine);
    }
}


