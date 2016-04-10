package com.iambedant.nanodegree.quickbite.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.iambedant.nanodegree.quickbite.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "_quick_bite_pref_file";

    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public String getString(String KEY, String DEFAULT){
        return mPref.getString(KEY,DEFAULT);
    }

    public void putString(String KEY, String VALUE){
        mPref.edit().putString(KEY,VALUE).apply();
    }

}
