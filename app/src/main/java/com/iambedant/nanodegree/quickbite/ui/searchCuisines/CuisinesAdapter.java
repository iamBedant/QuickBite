package com.iambedant.nanodegree.quickbite.ui.searchCuisines;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.model.Cuisines.Cuisine_;
import com.iambedant.nanodegree.quickbite.ui.base.BaseAdapter;
import com.iambedant.nanodegree.quickbite.ui.views.SearchItemView;
import com.iambedant.nanodegree.quickbite.util.Constants;

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
        return (SearchItemView) LayoutInflater.from(context)
                .inflate(R.layout.item_recycler_view_search, viewGroup, false);
    }

    @Override
    protected void bind(final Cuisine_ value, SearchItemView view, ViewHolder<SearchItemView> holder) {
        if (value != null) {
            view.setMainText(value.getCuisineName());
            view.setOnItemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.SEARCH_TERM, value.getCuisineId()+"");
                    ((Activity)mContext).setResult(Activity.RESULT_OK, intent);
                    ((Activity)mContext).finish();
                }
            });
        }
    }
}
