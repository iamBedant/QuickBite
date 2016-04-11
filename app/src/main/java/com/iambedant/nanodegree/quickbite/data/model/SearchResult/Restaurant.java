
package com.iambedant.nanodegree.quickbite.data.model.SearchResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Restaurant {

    @SerializedName("restaurant")
    @Expose
    private Restaurant_ restaurant;

    /**
     * 
     * @return
     *     The restaurant
     */
    public Restaurant_ getRestaurant() {
        return restaurant;
    }

    /**
     * 
     * @param restaurant
     *     The restaurant
     */
    public void setRestaurant(Restaurant_ restaurant) {
        this.restaurant = restaurant;
    }

}
