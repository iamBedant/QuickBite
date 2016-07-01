package com.iambedant.nanodegree.quickbite.ui.restaurant;

import com.iambedant.nanodegree.quickbite.data.model.Reviews.UserReview;
import com.iambedant.nanodegree.quickbite.ui.base.MvpView;

import java.util.ArrayList;

/**
 * Created by Kuliza-193 on 4/15/2016.
 */
public interface RestaurantMvpView extends MvpView {
    void showReviews(ArrayList<UserReview> mListReview);
    void updateAllWidgets();

}
