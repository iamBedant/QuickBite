
package com.iambedant.nanodegree.quickbite.data.model.Reviews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class User implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("zomato_handle")
    @Expose
    private String zomatoHandle;
    @SerializedName("foodie_level")
    @Expose
    private String foodieLevel;
    @SerializedName("foodie_level_num")
    @Expose
    private Integer foodieLevelNum;
    @SerializedName("foodie_color")
    @Expose
    private String foodieColor;
    @SerializedName("profile_url")
    @Expose
    private String profileUrl;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("profile_deeplink")
    @Expose
    private String profileDeeplink;

    /**
     *
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     *     The zomatoHandle
     */
    public String getZomatoHandle() {
        return zomatoHandle;
    }

    /**
     *
     * @param zomatoHandle
     *     The zomato_handle
     */
    public void setZomatoHandle(String zomatoHandle) {
        this.zomatoHandle = zomatoHandle;
    }

    /**
     *
     * @return
     *     The foodieLevel
     */
    public String getFoodieLevel() {
        return foodieLevel;
    }

    /**
     *
     * @param foodieLevel
     *     The foodie_level
     */
    public void setFoodieLevel(String foodieLevel) {
        this.foodieLevel = foodieLevel;
    }

    /**
     *
     * @return
     *     The foodieLevelNum
     */
    public Integer getFoodieLevelNum() {
        return foodieLevelNum;
    }

    /**
     *
     * @param foodieLevelNum
     *     The foodie_level_num
     */
    public void setFoodieLevelNum(Integer foodieLevelNum) {
        this.foodieLevelNum = foodieLevelNum;
    }

    /**
     *
     * @return
     *     The foodieColor
     */
    public String getFoodieColor() {
        return foodieColor;
    }

    /**
     *
     * @param foodieColor
     *     The foodie_color
     */
    public void setFoodieColor(String foodieColor) {
        this.foodieColor = foodieColor;
    }

    /**
     *
     * @return
     *     The profileUrl
     */
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     *
     * @param profileUrl
     *     The profile_url
     */
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    /**
     *
     * @return
     *     The profileImage
     */
    public String getProfileImage() {
        return profileImage;
    }

    /**
     *
     * @param profileImage
     *     The profile_image
     */
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    /**
     *
     * @return
     *     The profileDeeplink
     */
    public String getProfileDeeplink() {
        return profileDeeplink;
    }

    /**
     *
     * @param profileDeeplink
     *     The profile_deeplink
     */
    public void setProfileDeeplink(String profileDeeplink) {
        this.profileDeeplink = profileDeeplink;
    }


    protected User(Parcel in) {
        name = in.readString();
        zomatoHandle = in.readString();
        foodieLevel = in.readString();
        foodieLevelNum = in.readByte() == 0x00 ? null : in.readInt();
        foodieColor = in.readString();
        profileUrl = in.readString();
        profileImage = in.readString();
        profileDeeplink = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(zomatoHandle);
        dest.writeString(foodieLevel);
        if (foodieLevelNum == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(foodieLevelNum);
        }
        dest.writeString(foodieColor);
        dest.writeString(profileUrl);
        dest.writeString(profileImage);
        dest.writeString(profileDeeplink);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}