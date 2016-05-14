package com.iambedant.nanodegree.quickbite.ui.SplashScreen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.home.Home;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class SplashScreen extends BaseActivity implements SplashMvpView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static int SPLASH_TIME_OUT;
    Context mContext;
    private Location mLastLocation;
    private final String TAG = SplashScreen.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    @Inject
    SplashPresenter mSplashPresenter;

    private final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private final int REQUEST_GOOGLE_PLAY_SERVICES = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getActivityComponent().inject(this);
        SPLASH_TIME_OUT = 2000;
        mContext = this;
        mSplashPresenter.attachView(this);
        if (checkPlayServices()) {
            Logger.d(TAG, "play service found");
            onActivityResult(REQUEST_GOOGLE_PLAY_SERVICES, Activity.RESULT_OK, null);
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability gApi = GoogleApiAvailability.getInstance();
        int resultCode = gApi.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (gApi.isUserResolvableError(resultCode)) {
                gApi.getErrorDialog(this, resultCode, REQUEST_GOOGLE_PLAY_SERVICES).show();
            } else {
                //TODO: Manual Location Entry
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == Activity.RESULT_OK) {
                    Logger.d(TAG, "Inside OnActivityResult");
                    buildGoogleApiClient();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void getLocation() {
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions((Activity) mContext,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            Logger.d(TAG, "Getting Location");
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            handleLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    }


    public void handleLocation(Double lat, Double lon) {
        Logger.d(TAG, "Handling Location");
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            Address obj = addresses.get(0);
            Logger.d(TAG, lat+" "+ lon+" "+obj.getSubLocality()+", "+obj.getLocality());
            mSplashPresenter.saveLocation(lat, lon, obj.getSubLocality());

        } catch (IOException e) {
            e.printStackTrace();
            Logger.d(TAG, e.getMessage());
        }

        mSplashPresenter.loadCuisineslData(lat, lon);
    }

    protected synchronized void buildGoogleApiClient() {
        Logger.d(TAG, "Building API Client");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Logger.d(TAG, "Permission Granted");
                    buildGoogleApiClient();
                } else {
                    Logger.d(TAG, "Permission Denied");
                    //TODO: Menual Location Entry
                }
                return;
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mSplashPresenter.detachView();
    }

    @Override
    public void gotoManinScreen() {
        Intent intent = new Intent(mContext, Home.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void ShowErrorDialog() {
       showAlert(getString(R.string.error_title),getString(R.string.error_message_generic),getString(R.string.retry),getString(R.string.close));
    }

    public void showAlert(String title, String message,String positiveButton, String negativeButton){
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getLocation();
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        getLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
