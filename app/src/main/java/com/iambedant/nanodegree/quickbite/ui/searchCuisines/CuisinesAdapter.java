package com.iambedant.nanodegree.quickbite.ui.searchCuisines;

import android.content.Context;
import android.view.ViewGroup;

import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisine_;
import com.iambedant.nanodegree.quickbite.ui.base.BaseAdapter;
import com.iambedant.nanodegree.quickbite.ui.views.SearchItemView;

/**
 * Created by Kuliza-193 on 6/1/2016.
 */

public class CuisinesAdapter extends BaseAdapter<Cuisine_, SearchItemView> {

    Context mContext;

    public CuisinesAdapter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected SearchItemView createView(Context context, ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    protected void bind(Cuisine_ value, SearchItemView view, ViewHolder<SearchItemView> holder) {

    }
}
