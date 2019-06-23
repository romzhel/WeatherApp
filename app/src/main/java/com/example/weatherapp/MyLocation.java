package com.example.weatherapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.weatherapp.model.Coord;

import static android.content.Context.LOCATION_SERVICE;

public class MyLocation implements ActivityCompat.OnRequestPermissionsResultCallback {
    public static final String TAG = "myApp";
    public static final int PERMISSION_REQUEST_CODE = 10;
    public static final int LOCATION_MIN_TIME = 5000;
    public static final int LOCATION_MIN_DISTANCE = 10;
    private LocationManager locationManager;
    private OnCoordinatesChanges coordListener;
    private String provider;


    @SuppressLint("MissingPermission")
    public MyLocation(AppCompatActivity activity, OnCoordinatesChanges coordListener) {
        this.coordListener = coordListener;
        initLocationAndPermissions(activity);

        Log.d(TAG, "MyLocation constructor");

        if (provider != null) {
            locationManager.requestLocationUpdates(provider, LOCATION_MIN_TIME, LOCATION_MIN_DISTANCE, listener);
        }
    }

    private LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(android.location.Location location) {
            Coord coord = new Coord();
            coord.setLat(location.getLatitude());
            coord.setLon(location.getLongitude());

            coordListener.onCoordinatesChanges(coord);
            Log.d(TAG, "location changed");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG, "status changed to " + status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "provider enabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "provider disabled");
        }
    };

    private void initLocationAndPermissions(AppCompatActivity activity) {
        int finePermission = ActivityCompat.checkSelfPermission(
                MyApplication.getAppContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int coarsePermission = ActivityCompat.checkSelfPermission(
                MyApplication.getAppContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (finePermission == PackageManager.PERMISSION_GRANTED && coarsePermission == PackageManager.PERMISSION_GRANTED) {
            requestLocation();
        } else {
            requestLocationPermission(activity);
        }
    }

    private void requestLocation() {
        Log.d(TAG, "request location");

        int finePermission = ActivityCompat.checkSelfPermission(
                MyApplication.getAppContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int coarsePermission = ActivityCompat.checkSelfPermission(
                MyApplication.getAppContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (finePermission != PackageManager.PERMISSION_GRANTED && coarsePermission != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager = (LocationManager) MyApplication.getAppContext().getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        provider = locationManager.getBestProvider(criteria, true);
    }

    private void requestLocationPermission(AppCompatActivity activity) {
        Log.d(TAG, "request permission");
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CALL_PHONE)) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "request permission result");

        if (requestCode == PERMISSION_REQUEST_CODE) {
            Log.d(TAG, "request code = our code");

            for (int i = 0; i < permissions.length; i++) {
                Log.d(TAG, permissions[i] + " = " + grantResults[i]);
            }

            requestLocation();
        }
    }

    public void stop() {
        Log.d(TAG, "trying to stop location");

        locationManager.removeUpdates(listener);
    }

    public interface OnCoordinatesChanges {
        void onCoordinatesChanges(Coord coord);
    }

}
