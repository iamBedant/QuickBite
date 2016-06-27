package com.iambedant.nanodegree.quickbite.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.data.local.persistent.DataContract;

@SuppressLint("NewApi")
public class WidgetDataProvider implements RemoteViewsFactory {

//	List<String> mCollections = new ArrayList<String>();

	Context mContext = null;
	Cursor data = null;

	public WidgetDataProvider(Context context, Intent intent) {
		mContext = context;
	}

	@Override
	public int getCount() {
		return data == null ? 0 : data.getCount();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public RemoteViews getViewAt(int position) {

		if (position == AdapterView.INVALID_POSITION || data == null || !data.moveToPosition(position)) {
			return null;
		}


//		RemoteViews mView = new RemoteViews(mContext.getPackageName(),android.R.layout.simple_list_item_1);
//
//		mView.setTextViewText(android.R.id.text1, data.getString(data.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_NAME)));
//		mView.setTextColor(android.R.id.text1, Color.BLACK);

		RemoteViews mView = new RemoteViews(mContext.getPackageName(), R.layout.widget_favourite_item);
		mView.setTextViewText(R.id.tv_name, data.getString(data.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_NAME)));
//		mView.setTextColor(R.id.tv_name, Color.BLACK);
		mView.setTextViewText(R.id.tv_rating,""+ data.getInt(data.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_RATINGE)));
		mView.setTextViewText(R.id.tv_address, data.getString(data.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_ADDRESS)));
		mView.setTextViewText(R.id.tv_cuisines, data.getString(data.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_CUISINE)));

//		mView.setTextColor(R.id.tv_cuisines, Color.BLACK);


		final Intent fillInIntent = new Intent();
		fillInIntent.setAction(WidgetProvider.ACTION_TOAST);
		final Bundle bundle = new Bundle();
		bundle.putString(WidgetProvider.EXTRA_STRING,
				data.getString(data.getColumnIndex(DataContract.RestaurantEntry.COLUMN_RESTAURANT_NAME)));
		fillInIntent.putExtras(bundle);


//		mView.setOnClickFillInIntent(R.id.tv_name, fillInIntent);


		return mView;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public void onCreate() {
		initData();
	}

	@Override
	public void onDataSetChanged() {
		initData();
	}

	private void initData() {
//		mCollections.clear();
//		for (int i = 1; i <= 10; i++) {
//			mCollections.add("ListView item " + i);
//		}

		if (data != null) {
			data.close();
		}

		final long identityToken = Binder.clearCallingIdentity();
		// load today fixture
		data = mContext.getContentResolver().query(
				DataContract.RestaurantEntry.CONTENT_URI,
				null,
				null,
				null,
				null);

		// and restore the identity again
		Binder.restoreCallingIdentity(identityToken);
	}

	@Override
	public void onDestroy() {

	}

}
