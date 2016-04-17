
package com.iambedant.nanodegree.quickbite.data.model.Cuisines;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Cuisines {

    @SerializedName("cuisines")
    @Expose
    private List<Cuisine> cuisines = new ArrayList<Cuisine>();

    /**
     * 
     * @return
     *     The cuisines
     */
    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    /**
     * 
     * @param cuisines
     *     The cuisines
     */
    public void setCuisines(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }

}
