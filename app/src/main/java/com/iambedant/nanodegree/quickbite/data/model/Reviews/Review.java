
package com.iambedant.nanodegree.quickbite.data.model.Reviews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Review implements Parcelable {

    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("review_text")
    @Expose
    private String reviewText;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rating_color")
    @Expose
    private String ratingColor;
    @SerializedName("review_time_friendly")
    @Expose
    private String reviewTimeFriendly;
    @SerializedName("rating_text")
    @Expose
    private String ratingText;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("comments_count")
    @Expose
    private Integer commentsCount;

    /**
     *
     * @return
     *     The rating
     */
    public Double getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     *     The rating
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     *     The reviewText
     */
    public String getReviewText() {
        return reviewText;
    }

    /**
     *
     * @param reviewText
     *     The review_text
     */
    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    /**
     *
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The ratingColor
     */
    public String getRatingColor() {
        return ratingColor;
    }

    /**
     *
     * @param ratingColor
     *     The rating_color
     */
    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    /**
     *
     * @return
     *     The reviewTimeFriendly
     */
    public String getReviewTimeFriendly() {
        return reviewTimeFriendly;
    }

    /**
     *
     * @param reviewTimeFriendly
     *     The review_time_friendly
     */
    public void setReviewTimeFriendly(String reviewTimeFriendly) {
        this.reviewTimeFriendly = reviewTimeFriendly;
    }

    /**
     *
     * @return
     *     The ratingText
     */
    public String getRatingText() {
        return ratingText;
    }

    /**
     *
     * @param ratingText
     *     The rating_text
     */
    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    /**
     *
     * @return
     *     The timestamp
     */
    public Integer getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     *     The timestamp
     */
    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    /**
     *
     * @return
     *     The likes
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     *
     * @param likes
     *     The likes
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     *
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     *     The commentsCount
     */
    public Integer getCommentsCount() {
        return commentsCount;
    }

    /**
     *
     * @param commentsCount
     *     The comments_count
     */
    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }


    protected Review(Parcel in) {
        rating = in.readByte() == 0x00 ? null : in.readDouble();
        reviewText = in.readString();
        id = in.readString();
        ratingColor = in.readString();
        reviewTimeFriendly = in.readString();
        ratingText = in.readString();
        timestamp = in.readByte() == 0x00 ? null : in.readInt();
        likes = in.readByte() == 0x00 ? null : in.readInt();
        user = (User) in.readValue(User.class.getClassLoader());
        commentsCount = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (rating == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(rating);
        }
        dest.writeString(reviewText);
        dest.writeString(id);
        dest.writeString(ratingColor);
        dest.writeString(reviewTimeFriendly);
        dest.writeString(ratingText);
        if (timestamp == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(timestamp);
        }
        if (likes == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(likes);
        }
        dest.writeValue(user);
        if (commentsCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(commentsCount);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}