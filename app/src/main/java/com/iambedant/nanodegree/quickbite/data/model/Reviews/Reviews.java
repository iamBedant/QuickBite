
package com.iambedant.nanodegree.quickbite.data.model.Reviews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Reviews implements Parcelable {

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


    protected Reviews(Parcel in) {
        reviewsCount = in.readByte() == 0x00 ? null : in.readInt();
        reviewsStart = in.readByte() == 0x00 ? null : in.readInt();
        reviewsShown = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            userReviews = new ArrayList<UserReview>();
            in.readList(userReviews, UserReview.class.getClassLoader());
        } else {
            userReviews = null;
        }
        RespondToReviewsViaZomatoDashboard = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (reviewsCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(reviewsCount);
        }
        if (reviewsStart == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(reviewsStart);
        }
        if (reviewsShown == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(reviewsShown);
        }
        if (userReviews == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(userReviews);
        }
        dest.writeString(RespondToReviewsViaZomatoDashboard);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Reviews> CREATOR = new Parcelable.Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };
}