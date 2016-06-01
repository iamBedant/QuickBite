package com.iambedant.nanodegree.quickbite.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Kuliza-193 on 6/1/2016.
 */

public class SearchItemView extends RelativeLayout {
    public SearchItemView(Context context) {
        super(context);
    }

    public SearchItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//    private void initView(){
//        LayoutInflater inflater =
//    }

}
