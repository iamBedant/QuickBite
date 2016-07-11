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
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
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
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.iambedant.nanodegree.quickbite.R;
import com.iambedant.nanodegree.quickbite.ui.Login.LoginActivity;
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
    private static final int REQUEST_CODE_AUTOCOMPLETE = 4;
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
                //Calling Autocomplete Location
                openAutocompleteActivity();
            }
            return false;
        }
        return true;
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
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode == Activity.RESULT_OK) {
                    Logger.d(TAG, "Inside OnActivityResult");
                    buildGoogleApiClient();
                }
                break;

            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        openAutocompleteActivity();
                        break;
                    default:
                        break;
                }
                break;

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
                    showAlertForManualLocation(getString(R.string.sorry), getString(R.string.location_error_message),getString(R.string.location_positive),getString(R.string.location_negative));
                }

                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public void getLocation() {
        Logger.d(TAG,"Inside GetLocation");
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
//                    Manifest.permission.ACCESS_FINE_LOCATION)) {
//                Logger.d(TAG,"Inside Explaination");
//                // Show an expanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions((Activity) mContext,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
//            }
        } else {
            Logger.d(TAG, "Getting Location");
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation == null) {
                Logger.d(TAG, "Location Null");
                if (mGoogleApiClient.isConnected()) {
                    Logger.d(TAG, "Client Connected");
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequestHighAccuracy, this);
                } else {
                    Logger.d(TAG, "Client not connected");
                    mGoogleApiClient.connect();
                }

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
                    openAutocompleteActivity();
                }
                return;
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.d(TAG, "On Start Called");
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }

    @Override
    protected void onPause() {
        Logger.d(TAG, "On Pause Called");
        super.onPause();
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(TAG, "On Destroy Called");
        mGoogleApiClient.disconnect();
        mSplashPresenter.detachView();
    }

    @Override
    public void gotoManinScreen() {
        if (mSplashPresenter.isLoggedIn()) {
            Intent intent = new Intent(mContext, Home.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            finish();
        }
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



    public void showAlertForManualLocation(String title, String message, String positiveButton, String negativeButton) {
        new AlertDialog.Builder(mContext)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        openAutocompleteActivity();
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setIcon(R.drawable.ic_mood_bad_black_24dp)
                .show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Logger.d(TAG, " Now Connected");
        if (mLocationRequestHighAccuracy == null) {
            Logger.d(TAG, " mLocationRequest Null");
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
                        Logger.d(TAG,"All location settings are satisfied. The client can initialize location");
                        getLocation();

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        Logger.d(TAG,"Location settings are not satisfied. But could be fixed by showing the user");
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

                        Logger.d(TAG,"Location settings are not satisfied. However, we have no way to fix the");
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

    @Override
    protected void onStop() {
        super.onStop();

    }
}
