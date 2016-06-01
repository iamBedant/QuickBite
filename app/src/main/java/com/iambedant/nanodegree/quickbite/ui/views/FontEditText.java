package com.iambedant.nanodegree.quickbite.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.util.FontUtil;

/**
 * Created by Kuliza-193 on 6/1/2016.
 */

public class FontEditText extends EditText {
    public FontEditText(Context context) {
        super(context);
        init(context, null);
    }

    public FontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FontEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);

        if (a.hasValue(R.styleable.FontTextView_android_textAppearance)) {
            final int textAppearanceId = a.getResourceId(R.styleable
                            .FontTextView_android_textAppearance,
                    android.R.style.TextAppearance);
            TypedArray atp = getContext().obtainStyledAttributes(textAppearanceId,
                    R.styleable.FontTextAppearance);
            if (atp.hasValue(R.styleable.FontTextAppearance_font)) {
                setFont(atp.getString(R.styleable.FontTextAppearance_font));
            }
            atp.recycle();
        }

        if (a.hasValue(R.styleable.FontTextView_font)) {
            setFont(a.getString(R.styleable.FontTextView_font));
        }
        a.recycle();
    }

    public void setFont(String font) {
        setPaintFlags(getPaintFlags() | Paint.ANTI_ALIAS_FLAG);
        setTypeface(FontUtil.get(getContext(), font));
    }
}
