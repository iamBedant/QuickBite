
package com.iambedant.nanodegree.quickbite.data.model.Cuisines;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Cuisine_ {

    @SerializedName("cuisine_id")
    @Expose
    private Integer cuisineId;
    @SerializedName("cuisine_name")
    @Expose
    private String cuisineName;

    /**
     * 
     * @return
     *     The cuisineId
     */
    public Integer getCuisineId() {
        return cuisineId;
    }

    /**
     * 
     * @param cuisineId
     *     The cuisine_id
     */
    public void setCuisineId(Integer cuisineId) {
        this.cuisineId = cuisineId;
    }

    /**
     * 
     * @return
     *     The cuisineName
     */
    public String getCuisineName() {
        return cuisineName;
    }

    /**
     * 
     * @param cuisineName
     *     The cuisine_name
     */
    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

}
