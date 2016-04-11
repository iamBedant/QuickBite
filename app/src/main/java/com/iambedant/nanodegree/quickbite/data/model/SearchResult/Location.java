
package com.iambedant.nanodegree.quickbite.data.model.SearchResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Location {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("city_id")
    @Expose
    private Integer cityId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("country_id")
    @Expose
    private Integer countryId;

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The locality
     */
    public String getLocality() {
        return locality;
    }

    /**
     * 
     * @param locality
     *     The locality
     */
    public void setLocality(String locality) {
        this.locality = locality;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The cityId
     */
    public Integer getCityId() {
        return cityId;
    }

    /**
     * 
     * @param cityId
     *     The city_id
     */
    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
     * 
     * @return
     *     The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 
     * @param latitude
     *     The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 
     * @return
     *     The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 
     * @param longitude
     *     The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 
     * @return
     *     The zipcode
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * 
     * @param zipcode
     *     The zipcode
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /**
     * 
     * @return
     *     The countryId
     */
    public Integer getCountryId() {
        return countryId;
    }

    /**
     * 
     * @param countryId
     *     The country_id
     */
    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

}
