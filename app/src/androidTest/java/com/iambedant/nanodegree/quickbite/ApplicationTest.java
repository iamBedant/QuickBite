package com.iambedant.nanodegree.quickbite;

import android.app.Application;
import android.database.Cursor;
import android.test.ApplicationTestCase;

import com.iambedant.nanodegree.quickbite.data.local.persistent.DataContract;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    public void setUp() throws Exception {
        createApplication();
    }

    public void testgetAllMessaages() throws Exception {

        Cursor c = getContext().getContentResolver().query(DataContract.RestaurantEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
        int n = c.getCount();
        assertEquals(3,n);
    }

}