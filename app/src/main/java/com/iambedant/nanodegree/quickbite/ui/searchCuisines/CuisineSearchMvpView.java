package com.iambedant.nanodegree.quickbite.ui.searchCuisines;

import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisine_;
import com.iambedant.nanodegree.quickbite.ui.base.MvpView;

import java.util.List;

/**
 * Created by Kuliza-193 on 6/1/2016.
 */

public interface CuisineSearchMvpView extends MvpView {

    public void displayCuisines(List<Cuisine_> cuisine);

}
