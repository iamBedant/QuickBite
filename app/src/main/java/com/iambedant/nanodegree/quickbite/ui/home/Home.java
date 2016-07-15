package com.iambedant.nanodegree.quickbite.ui.home;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.events.EventName;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.favourites.Favourites;
import com.iambedant.nanodegree.quickbite.ui.list.ListActivity;
import com.iambedant.nanodegree.quickbite.ui.settings.Settings;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Home extends BaseActivity implements HomeMvpView {

    private final String TAG = Home.class.getSimpleName();

    @Inject
    HomePresenter mHomePresenter;

    @Bind(R.id.tv_location)
    TextView mTextViewLocation;

    @Bind(R.id.tv_user_name)
    TextView mTextViewUserName;

    Context mContext;

    private static final int REQUEST_CODE_AUTOCOMPLETE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mContext = this;
        mHomePresenter.attachView(this);
        mHomePresenter.loadLastKnownLocation();

    }


    @Override
    protected void onResume() {
        super.onResume();
        mHomePresenter.getUserName();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mHomePresenter.detachView();
    }

    @Subscribe
    public void onEvent(EventName eventName) {
        mTextViewUserName.setText("Hi " + eventName.name);
    }

    @OnClick(R.id.rl_lunch)
    public void openLunchActivity() {

        Intent intent = new Intent(mContext, ListActivity.class);
        intent.putExtra(Constants.TYPE_EXTRA_KEY, Constants.TYPE_TAKE_AWAY);
        startActivity(intent);

    }

    @OnClick(R.id.rl_dinner)
    public void openDinnerActivity() {
        Intent intent = new Intent(mContext, ListActivity.class);
        intent.putExtra(Constants.TYPE_EXTRA_KEY, Constants.TYPE_DINNER);
        startActivity(intent);

    }

    @OnClick(R.id.rl_breakfast)
    public void openBreakfastActivity() {
        Intent intent = new Intent(mContext, ListActivity.class);
        intent.putExtra(Constants.TYPE_EXTRA_KEY, Constants.TYPE_BREAKFAST);
        startActivity(intent);

    }

    @OnClick(R.id.rl_bar)
    public void openBarActivity() {
        Intent intent = new Intent(mContext, ListActivity.class);
        intent.putExtra(Constants.TYPE_EXTRA_KEY, Constants.TYPE_BAR);
        startActivity(intent);

    }

    @OnClick(R.id.rl_coffee)
    public void openCoffeeActivity() {
        Intent intent = new Intent(mContext, ListActivity.class);
        intent.putExtra(Constants.TYPE_EXTRA_KEY, Constants.TYPE_COFFEE);
        startActivity(intent);

    }

    @OnClick(R.id.rl_favourite)
    public void openFavouriteActivity() {
        Intent intent = new Intent(mContext, Favourites.class);
        startActivity(intent);
    }

    @Override
    public void showLocation(String location) {

        Logger.d(TAG, "Setting Location " + location);
        if (!location.isEmpty()) {
            mTextViewLocation.setText(location);
        }
    }

    @OnClick(R.id.btn_location)
    public void selectLocation() {
        openAutocompleteActivity();
    }

    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e(TAG, message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_AUTOCOMPLETE:
                if (resultCode == RESULT_OK) {
                    // Get the user's selected place from the Intent.
                    Place place = PlaceAutocomplete.getPlace(this, data);
                    Logger.i(TAG, "Place Selected: " + place.getName());
                    handleLocation(place.getLatLng().latitude, place.getLatLng().longitude);

                } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                    Status status = PlaceAutocomplete.getStatus(this, data);
                    Logger.e(TAG, "Error: Status = " + status.toString());
                } else if (resultCode == RESULT_CANCELED) {
                    //dont do anything
                }

                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void handleLocation(Double lat, Double lon) {
        Logger.d(TAG, "Handling Location");
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            Address obj = addresses.get(0);
            Logger.d(TAG, lat + " " + lon + " " + obj.getSubLocality() + ", " + obj.getLocality());
            mHomePresenter.saveLocation(lat, lon, obj.getSubLocality());

        } catch (IOException e) {
            e.printStackTrace();
            Logger.d(TAG, e.getMessage());
        }

    }

    @OnClick(R.id.tv_user_name)
    public void openSettings() {
        Intent intent = new Intent(mContext, Settings.class);
        startActivity(intent);
    }

}
