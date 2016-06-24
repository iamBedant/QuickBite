package com.iambedant.nanodegree.quickbite.events;

/**
 * Created by Kuliza-193 on 6/23/2016.
 */

public class EventLoginSuccessfull {
    public Boolean isSuccessfull;
    public String message;

    public EventLoginSuccessfull(Boolean isSuccessfull, String message) {
        this.isSuccessfull = isSuccessfull;
        this.message = message;
    }
}
