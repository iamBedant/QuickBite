
package com.iambedant.nanodegree.quickbite.data.model.Cuisines;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Cuisine {

    @SerializedName("cuisine")
    @Expose
    private Cuisine_ cuisine;

    /**
     * 
     * @return
     *     The cuisine
     */
    public Cuisine_ getCuisine() {
        return cuisine;
    }

    /**
     * 
     * @param cuisine
     *     The cuisine
     */
    public void setCuisine(Cuisine_ cuisine) {
        this.cuisine = cuisine;
    }

}
