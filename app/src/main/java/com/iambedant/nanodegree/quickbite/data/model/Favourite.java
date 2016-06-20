package com.iambedant.nanodegree.quickbite.data.model;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kuliza-193 on 6/14/2016.
 */

public class Favourite {
    String restaurantId;
    String restaurantName;
    String coverImage;
    String cuisine;
    String address;
    String lat;
    String lon;
    String rating;
    int price;


    public Favourite() {
    }


    public Favourite(String restaurantId, String restaurantName, String coverImage, String cuisine, String address, String lat, String lon, String rating, int price) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.coverImage = coverImage;
        this.cuisine = cuisine;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
        this.rating = rating;
        this.price = price;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("restaurantId", restaurantId);
        result.put("restaurantName", restaurantName);
        result.put("coverImage", coverImage);
        result.put("cuisine", cuisine);
        result.put("address", address);
        result.put("lat", lat);
        result.put("lon", lon);
        result.put("rating", rating);
        result.put("price", price);

        return result;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
