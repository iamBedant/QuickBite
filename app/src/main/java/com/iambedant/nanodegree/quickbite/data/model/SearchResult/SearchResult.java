
package com.iambedant.nanodegree.quickbite.data.model.SearchResult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SearchResult implements Parcelable {

    @SerializedName("results_found")
    @Expose
    private Integer resultsFound;
    @SerializedName("results_start")
    @Expose
    private Integer resultsStart;
    @SerializedName("results_shown")
    @Expose
    private Integer resultsShown;
    @SerializedName("restaurants")
    @Expose
    private List<Restaurant> restaurants = new ArrayList<Restaurant>();

    /**
     *
     * @return
     *     The resultsFound
     */
    public Integer getResultsFound() {
        return resultsFound;
    }

    /**
     *
     * @param resultsFound
     *     The results_found
     */
    public void setResultsFound(Integer resultsFound) {
        this.resultsFound = resultsFound;
    }

    /**
     *
     * @return
     *     The resultsStart
     */
    public Integer getResultsStart() {
        return resultsStart;
    }

    /**
     *
     * @param resultsStart
     *     The results_start
     */
    public void setResultsStart(Integer resultsStart) {
        this.resultsStart = resultsStart;
    }

    /**
     *
     * @return
     *     The resultsShown
     */
    public Integer getResultsShown() {
        return resultsShown;
    }

    /**
     *
     * @param resultsShown
     *     The results_shown
     */
    public void setResultsShown(Integer resultsShown) {
        this.resultsShown = resultsShown;
    }

    /**
     *
     * @return
     *     The restaurants
     */
    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    /**
     *
     * @param restaurants
     *     The restaurants
     */
    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }


    protected SearchResult(Parcel in) {
        resultsFound = in.readByte() == 0x00 ? null : in.readInt();
        resultsStart = in.readByte() == 0x00 ? null : in.readInt();
        resultsShown = in.readByte() == 0x00 ? null : in.readInt();
        if (in.readByte() == 0x01) {
            restaurants = new ArrayList<Restaurant>();
            in.readList(restaurants, Restaurant.class.getClassLoader());
        } else {
            restaurants = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (resultsFound == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(resultsFound);
        }
        if (resultsStart == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(resultsStart);
        }
        if (resultsShown == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(resultsShown);
        }
        if (restaurants == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(restaurants);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SearchResult> CREATOR = new Parcelable.Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };
}