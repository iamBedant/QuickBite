
package com.iambedant.nanodegree.quickbite.data.model.Reviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Reviews {

    @SerializedName("reviews_count")
    @Expose
    private Integer reviewsCount;
    @SerializedName("reviews_start")
    @Expose
    private Integer reviewsStart;
    @SerializedName("reviews_shown")
    @Expose
    private Integer reviewsShown;
    @SerializedName("user_reviews")
    @Expose
    private List<UserReview> userReviews = new ArrayList<UserReview>();
    @SerializedName("Respond to reviews via Zomato Dashboard")
    @Expose
    private String RespondToReviewsViaZomatoDashboard;

    /**
     * 
     * @return
     *     The reviewsCount
     */
    public Integer getReviewsCount() {
        return reviewsCount;
    }

    /**
     * 
     * @param reviewsCount
     *     The reviews_count
     */
    public void setReviewsCount(Integer reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    /**
     * 
     * @return
     *     The reviewsStart
     */
    public Integer getReviewsStart() {
        return reviewsStart;
    }

    /**
     * 
     * @param reviewsStart
     *     The reviews_start
     */
    public void setReviewsStart(Integer reviewsStart) {
        this.reviewsStart = reviewsStart;
    }

    /**
     * 
     * @return
     *     The reviewsShown
     */
    public Integer getReviewsShown() {
        return reviewsShown;
    }

    /**
     * 
     * @param reviewsShown
     *     The reviews_shown
     */
    public void setReviewsShown(Integer reviewsShown) {
        this.reviewsShown = reviewsShown;
    }

    /**
     * 
     * @return
     *     The userReviews
     */
    public List<UserReview> getUserReviews() {
        return userReviews;
    }

    /**
     * 
     * @param userReviews
     *     The user_reviews
     */
    public void setUserReviews(List<UserReview> userReviews) {
        this.userReviews = userReviews;
    }

    /**
     * 
     * @return
     *     The RespondToReviewsViaZomatoDashboard
     */
    public String getRespondToReviewsViaZomatoDashboard() {
        return RespondToReviewsViaZomatoDashboard;
    }

    /**
     * 
     * @param RespondToReviewsViaZomatoDashboard
     *     The Respond to reviews via Zomato Dashboard
     */
    public void setRespondToReviewsViaZomatoDashboard(String RespondToReviewsViaZomatoDashboard) {
        this.RespondToReviewsViaZomatoDashboard = RespondToReviewsViaZomatoDashboard;
    }

}
