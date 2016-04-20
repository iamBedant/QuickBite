
package com.iambedant.nanodegree.quickbite.data.model.SearchResult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Restaurant implements Parcelable {

    @SerializedName("restaurant")
    @Expose
    private Restaurant_ restaurant;

    /**
     *
     * @return
     *     The restaurant
     */
    public Restaurant_ getRestaurant() {
        return restaurant;
    }

    /**
     *
     * @param restaurant
     *     The restaurant
     */
    public void setRestaurant(Restaurant_ restaurant) {
        this.restaurant = restaurant;
    }


    protected Restaurant(Parcel in) {
        restaurant = (Restaurant_) in.readValue(Restaurant_.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(restaurant);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}