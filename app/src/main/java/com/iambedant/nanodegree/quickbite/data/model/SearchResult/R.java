
package com.iambedant.nanodegree.quickbite.data.model.SearchResult;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class R implements Parcelable {

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


    protected R(Parcel in) {
        resId = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (resId == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(resId);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<R> CREATOR = new Parcelable.Creator<R>() {
        @Override
        public R createFromParcel(Parcel in) {
            return new R(in);
        }

        @Override
        public R[] newArray(int size) {
            return new R[size];
        }
    };
}