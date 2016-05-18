package com.iambedant.nanodegree.quickbite.ui.SplashScreen;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.base.BaseActivity;
import com.iambedant.nanodegree.quickbite.ui.home.Home;
import com.iambedant.nanodegree.quickbite.util.Constants;
import com.iambedant.nanodegree.quickbite.util.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class SplashScreen extends BaseActivity implements SplashMvpView, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    Context mContext;
    private Location mLastLocation;
    private final String TAG = SplashScreen.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    @Inject
    SplashPresenter mSplashPresenter;
    private LocationRequest mLocationRequestHighAccuracy;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private final int REQUEST_GOOGLE_PLAY_SERVICES = 2;
    private final int REQUEST_CHECK_SETTINGS = 3;
    LocationSettingsRequest.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getActivityComponent().inject(this);
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

            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        // TODO: Manual location
                        break;
                    default:
                        break;
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
            if (mLastLocation == null) {
                Logger.d(TAG, "Location Null");

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequestHighAccuracy, this);


            } else {
                handleLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            }

        }
    }

    public void handleLocation(Double lat, Double lon) {
        Logger.d(TAG, "Handling Location");
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            Address obj = addresses.get(0);
            Logger.d(TAG, lat + " " + lon + " " + obj.getSubLocality() + ", " + obj.getLocality());
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
        mGoogleApiClient.connect();
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
        Logger.d(TAG,"On Start Called");
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        Logger.d(TAG,"On Pause Called");
        super.onPause();
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d(TAG,"On Destroy Called");
        mGoogleApiClient.disconnect();
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
        showAlert(getString(R.string.error_title), getString(R.string.error_message_generic), getString(R.string.retry), getString(R.string.close));
    }

    public void showAlert(String title, String message, String positiveButton, String negativeButton) {
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

        Logger.d(TAG," Now Connected");
        if (mLocationRequestHighAccuracy == null) {
            createLocationRequest();
        }

        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequestHighAccuracy);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        getLocation();

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    (Activity) mContext,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.

                        break;
                }
            }
        });


    }

    private void showNotification() {
        // Set the Intent action to open Location Settings
        Intent gpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

        // Create a PendingIntent to start an Activity
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, gpsIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Create a notification builder that's compatible with platforms >= version 4
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext());

        // Set the title, text, and icon
        builder.setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.get_message))
                .setSmallIcon(R.drawable.ic_action_arrow)
                .setAutoCancel(true)
                // Get the Intent that starts the Location settings panel
                .setContentIntent(pendingIntent);

        // Get an instance of the Notification Manager
        NotificationManager notifyManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        // Build the notification and post it
        notifyManager.notify(0, builder.build());
    }


    private void createLocationRequest() {
        mLocationRequestHighAccuracy = new LocationRequest();
        mLocationRequestHighAccuracy.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequestHighAccuracy.setInterval(Constants.LOCATION_INTERVAL_MILLISECONDS);
        mLocationRequestHighAccuracy.setFastestInterval(Constants.LOCATION_INTERVAL_MILLISECONDS / 10);
        
//        mLocationRequestBalancedPowerAccuracy = new LocationRequest();
//        mLocationRequestBalancedPowerAccuracy.setInterval(Constants.LOCATION_INTERVAL_MILLISECONDS);
//        mLocationRequestBalancedPowerAccuracy.setFastestInterval(Constants.LOCATION_INTERVAL_MILLISECONDS / 100);
//        mLocationRequestBalancedPowerAccuracy.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }


    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        System.out.println("Connection failed");
        if (!mGoogleApiClient.isConnecting() &&
                !mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        getLocation();
    }
}
