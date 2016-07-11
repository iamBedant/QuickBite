package com.iambedant.nanodegree.quickbite.events;

/**
 * Created by iamBedant on 7/10/2016.
 */
public class EventError {
    public int TYPE;
    public String message;

    public EventError(int type, String message) {
        this.TYPE = type;
        this.message = message;
    }
}
