package com.iambedant.nanodegree.quickbite.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iambedant.nanodegree.quickbite.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kuliza-193 on 6/1/2016.
 */

public class SearchItemView extends RelativeLayout {


    @Bind(R.id.tv_main_text)
    TextView mTextViewName;

    @Bind(R.id.item)
    RelativeLayout mItemView;

    private View inflatedView;

    public SearchItemView(Context context) {
        super(context);
        initView();
    }

    public SearchItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SearchItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflatedView = inflater.inflate(R.layout.item_cuisine, this, true);
        ButterKnife.bind(this, inflatedView);
    }


    public void setMainText(String name) {
        mTextViewName.setText(name);
    }

    public void setOnItemClickListener(OnClickListener listener) {
        mItemView.setOnClickListener(listener);
    }
}
