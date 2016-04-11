
package com.iambedant.nanodegree.quickbite.data.model.SearchResult;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Restaurant_ {

    @SerializedName("R")
    @Expose
    private com.iambedant.nanodegree.quickbite.data.model.SearchResult.R R;
    @SerializedName("apikey")
    @Expose
    private String apikey;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("cuisines")
    @Expose
    private String cuisines;
    @SerializedName("average_cost_for_two")
    @Expose
    private Integer averageCostForTwo;
    @SerializedName("price_range")
    @Expose
    private Integer priceRange;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("offers")
    @Expose
    private List<Object> offers = new ArrayList<Object>();
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("user_rating")
    @Expose
    private UserRating userRating;
    @SerializedName("photos_url")
    @Expose
    private String photosUrl;
    @SerializedName("menu_url")
    @Expose
    private String menuUrl;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("has_online_delivery")
    @Expose
    private Integer hasOnlineDelivery;
    @SerializedName("is_delivering_now")
    @Expose
    private Integer isDeliveringNow;
    @SerializedName("deeplink")
    @Expose
    private String deeplink;
    @SerializedName("order_url")
    @Expose
    private String orderUrl;
    @SerializedName("order_deeplink")
    @Expose
    private String orderDeeplink;
    @SerializedName("events_url")
    @Expose
    private String eventsUrl;
    @SerializedName("establishment_types")
    @Expose
    private List<Object> establishmentTypes = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The R
     */
    public com.iambedant.nanodegree.quickbite.data.model.SearchResult.R getR() {
        return R;
    }

    /**
     * 
     * @param R
     *     The R
     */
    public void setR(com.iambedant.nanodegree.quickbite.data.model.SearchResult.R R) {
        this.R = R;
    }

    /**
     * 
     * @return
     *     The apikey
     */
    public String getApikey() {
        return apikey;
    }

    /**
     * 
     * @param apikey
     *     The apikey
     */
    public void setApikey(String apikey) {
        this.apikey = apikey;
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
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * 
     * @return
     *     The cuisines
     */
    public String getCuisines() {
        return cuisines;
    }

    /**
     * 
     * @param cuisines
     *     The cuisines
     */
    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    /**
     * 
     * @return
     *     The averageCostForTwo
     */
    public Integer getAverageCostForTwo() {
        return averageCostForTwo;
    }

    /**
     * 
     * @param averageCostForTwo
     *     The average_cost_for_two
     */
    public void setAverageCostForTwo(Integer averageCostForTwo) {
        this.averageCostForTwo = averageCostForTwo;
    }

    /**
     * 
     * @return
     *     The priceRange
     */
    public Integer getPriceRange() {
        return priceRange;
    }

    /**
     * 
     * @param priceRange
     *     The price_range
     */
    public void setPriceRange(Integer priceRange) {
        this.priceRange = priceRange;
    }

    /**
     * 
     * @return
     *     The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 
     * @param currency
     *     The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 
     * @return
     *     The offers
     */
    public List<Object> getOffers() {
        return offers;
    }

    /**
     * 
     * @param offers
     *     The offers
     */
    public void setOffers(List<Object> offers) {
        this.offers = offers;
    }

    /**
     * 
     * @return
     *     The thumb
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * 
     * @param thumb
     *     The thumb
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    /**
     * 
     * @return
     *     The userRating
     */
    public UserRating getUserRating() {
        return userRating;
    }

    /**
     * 
     * @param userRating
     *     The user_rating
     */
    public void setUserRating(UserRating userRating) {
        this.userRating = userRating;
    }

    /**
     * 
     * @return
     *     The photosUrl
     */
    public String getPhotosUrl() {
        return photosUrl;
    }

    /**
     * 
     * @param photosUrl
     *     The photos_url
     */
    public void setPhotosUrl(String photosUrl) {
        this.photosUrl = photosUrl;
    }

    /**
     * 
     * @return
     *     The menuUrl
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * 
     * @param menuUrl
     *     The menu_url
     */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    /**
     * 
     * @return
     *     The featuredImage
     */
    public String getFeaturedImage() {
        return featuredImage;
    }

    /**
     * 
     * @param featuredImage
     *     The featured_image
     */
    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    /**
     * 
     * @return
     *     The hasOnlineDelivery
     */
    public Integer getHasOnlineDelivery() {
        return hasOnlineDelivery;
    }

    /**
     * 
     * @param hasOnlineDelivery
     *     The has_online_delivery
     */
    public void setHasOnlineDelivery(Integer hasOnlineDelivery) {
        this.hasOnlineDelivery = hasOnlineDelivery;
    }

    /**
     * 
     * @return
     *     The isDeliveringNow
     */
    public Integer getIsDeliveringNow() {
        return isDeliveringNow;
    }

    /**
     * 
     * @param isDeliveringNow
     *     The is_delivering_now
     */
    public void setIsDeliveringNow(Integer isDeliveringNow) {
        this.isDeliveringNow = isDeliveringNow;
    }

    /**
     * 
     * @return
     *     The deeplink
     */
    public String getDeeplink() {
        return deeplink;
    }

    /**
     * 
     * @param deeplink
     *     The deeplink
     */
    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    /**
     * 
     * @return
     *     The orderUrl
     */
    public String getOrderUrl() {
        return orderUrl;
    }

    /**
     * 
     * @param orderUrl
     *     The order_url
     */
    public void setOrderUrl(String orderUrl) {
        this.orderUrl = orderUrl;
    }

    /**
     * 
     * @return
     *     The orderDeeplink
     */
    public String getOrderDeeplink() {
        return orderDeeplink;
    }

    /**
     * 
     * @param orderDeeplink
     *     The order_deeplink
     */
    public void setOrderDeeplink(String orderDeeplink) {
        this.orderDeeplink = orderDeeplink;
    }

    /**
     * 
     * @return
     *     The eventsUrl
     */
    public String getEventsUrl() {
        return eventsUrl;
    }

    /**
     * 
     * @param eventsUrl
     *     The events_url
     */
    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    /**
     * 
     * @return
     *     The establishmentTypes
     */
    public List<Object> getEstablishmentTypes() {
        return establishmentTypes;
    }

    /**
     * 
     * @param establishmentTypes
     *     The establishment_types
     */
    public void setEstablishmentTypes(List<Object> establishmentTypes) {
        this.establishmentTypes = establishmentTypes;
    }

}
