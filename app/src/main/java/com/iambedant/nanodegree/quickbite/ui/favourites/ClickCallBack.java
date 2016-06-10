package com.iambedant.nanodegree.quickbite.ui.favourites;

/**
 * Created by Kuliza-193 on 6/9/2016.
 */

public interface ClickCallBack {
    public void favouriteIconClicked(String id);

    public void directionClicked(Double lon, Double lat);

    public void zomatoClicked(String id);
}
