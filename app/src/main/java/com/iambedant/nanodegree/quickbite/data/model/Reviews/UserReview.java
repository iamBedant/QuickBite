
package com.iambedant.nanodegree.quickbite.data.model.Reviews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class UserReview implements Parcelable {

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


    protected UserReview(Parcel in) {
        review = (Review) in.readValue(Review.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(review);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserReview> CREATOR = new Parcelable.Creator<UserReview>() {
        @Override
        public UserReview createFromParcel(Parcel in) {
            return new UserReview(in);
        }

        @Override
        public UserReview[] newArray(int size) {
            return new UserReview[size];
        }
    };
}