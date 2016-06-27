package com.iambedant.nanodegree.quickbite.ui.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.iambedant.nanodegree.quickbite.R;


public class WidgetProvider extends AppWidgetProvider {

	public static final String ACTION_TOAST = "com.dharmangsoni.widgets.ACTION_TOAST";
	public static final String EXTRA_STRING = "com.dharmangsoni.widgets.EXTRA_STRING";

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(ACTION_TOAST)) {
			String item = intent.getExtras().getString(EXTRA_STRING);
			Toast.makeText(context, item, Toast.LENGTH_LONG).show();
		}
		super.onReceive(context, intent);
	}

	@SuppressLint("NewApi")
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		for (int widgetId : appWidgetIds) {
			RemoteViews mView = initViews(context, appWidgetManager, widgetId);

			// Adding collection list item handler
			final Intent onItemClick = new Intent(context, WidgetProvider.class);
			onItemClick.setAction(ACTION_TOAST);
			onItemClick.setData(Uri.parse(onItemClick
					.toUri(Intent.URI_INTENT_SCHEME)));
			final PendingIntent onClickPendingIntent = PendingIntent
					.getBroadcast(context, 0, onItemClick,
							PendingIntent.FLAG_UPDATE_CURRENT);
			mView.setPendingIntentTemplate(R.id.widgetCollectionList,
					onClickPendingIntent);

			appWidgetManager.updateAppWidget(widgetId, mView);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private RemoteViews initViews(Context context,
			AppWidgetManager widgetManager, int widgetId) {

		RemoteViews mView = new RemoteViews(context.getPackageName(),
				R.layout.widget_layout);

		Intent intent = new Intent(context, WidgetService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

		intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
		mView.setRemoteAdapter(widgetId, R.id.widgetCollectionList, intent);

		return mView;
	}
}
