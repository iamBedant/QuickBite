package com.iambedant.nanodegree.quickbite.ui.detail;

import com.iambedant.nanodegree.quickbite.data.model.Reviews.UserReview;
import com.iambedant.nanodegree.quickbite.ui.base.MvpView;

import java.util.List;

/**
 * Created by Kuliza-193 on 4/15/2016.
 */
public interface DetailMvpView extends MvpView {
    void showReviews(List<UserReview> mListReview);
}
