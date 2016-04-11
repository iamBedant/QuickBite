
package com.iambedant.nanodegree.quickbite.data.model.SearchResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class R {

    @SerializedName("res_id")
    @Expose
    private Integer resId;

    /**
     * 
     * @return
     *     The resId
     */
    public Integer getResId() {
        return resId;
    }

    /**
     * 
     * @param resId
     *     The res_id
     */
    public void setResId(Integer resId) {
        this.resId = resId;
    }

}
