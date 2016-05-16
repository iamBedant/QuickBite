package com.iambedant.nanodegree.quickbite.ui.favourites;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iambedant.nanodegree.quickbite.R;

/**
 * Created by Kuliza-193 on 5/16/2016.
 */
public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteAdapterViewHolder> {

    private Cursor mCursor;
    final private Context mContext;
   // final private FavouriteAdapterOnClickHandler mClickHandler;

    public FavouriteAdapter(Context context) {
        mContext = context;


    }

    @Override
    public FavouriteAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant_view, parent, false);
        view.setFocusable(true);
        return new FavouriteAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavouriteAdapterViewHolder holder, int position) {
        mCursor.moveToPosition(position);
    }

    @Override
    public int getItemCount() {
        if ( null == mCursor ) return 0;
        return mCursor.getCount();
    }

    public class FavouriteAdapterViewHolder extends RecyclerView.ViewHolder {
        public FavouriteAdapterViewHolder(View view) {
            super(view);

//            view.setOnClickListener(this);
        }


    }



    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
      //  mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public static interface FavouriteAdapterOnClickHandler {
        void onClick(Long date, FavouriteAdapterViewHolder vh);
    }

}
