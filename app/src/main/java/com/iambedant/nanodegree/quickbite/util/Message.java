package com.iambedant.nanodegree.quickbite.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Kuliza-193 on 4/10/2016.
 */
public class Message {
    public static void display(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
