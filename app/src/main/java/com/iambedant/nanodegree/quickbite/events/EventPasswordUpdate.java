package com.iambedant.nanodegree.quickbite.events;

/**
 * Created by iamBedant on 7/15/2016.
 */
public class EventPasswordUpdate {
    public Boolean isSuccessfull;
    public String message;

    public EventPasswordUpdate(Boolean isSuccessfull, String message) {
        this.isSuccessfull = isSuccessfull;
        this.message  = message;
    }
}
