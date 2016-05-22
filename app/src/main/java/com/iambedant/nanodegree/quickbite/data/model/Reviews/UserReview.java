
package com.iambedant.nanodegree.quickbite.data.model.Reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class UserReview {

    @SerializedName("review")
    @Expose
    private Review review;

    /**
     * 
     * @return
     *     The review
     */
    public Review getReview() {
        return review;
    }

    /**
     * 
     * @param review
     *     The review
     */
    public void setReview(Review review) {
        this.review = review;
    }

}
