package com.example.darshangada.geolocationdemo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

public class AppLocationService extends Service implements LocationListener {

    protected LocationManager locationManager;
    Location location;
    Context context;

    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;
    private static Context mcontext;


    public AppLocationService(Context mcontext) {
        this.mcontext = mcontext;
        locationManager = (LocationManager) mcontext
                .getSystemService(LOCATION_SERVICE);
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions((Activity) mcontext,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                        , Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
    }

    public Location getLocation(String provider) {
        if (locationManager.isProviderEnabled(provider)) {

            if (ActivityCompat.checkSelfPermission((Activity) mcontext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                makeRequest();
            }
/*            if (Build.VERSION.SDK_INT >= 23) {

                if (mcontext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.e("testing", "Permission is granted");


                } else {

                    ActivityCompat.requestPermissions((Activity) mcontext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    Log.e("testing", "Permission is revoked");
                }
            } else { //permission is automatically granted on sdk<23 upon installation
                Log.e("testing", "Permission is already granted");
            }*/
            else {

                locationManager.requestLocationUpdates(provider,
                        MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(provider);
                    return location;
                }
            }
        }
        return location;

    }

    public boolean checkLocationPermission() {
        String permission = "android.permission.ACCESS_FINE_LOCATION";
        int res = this.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}