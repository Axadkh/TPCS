package com.police.org.tpcs;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;


class GetLocation extends Service{
    Context context;
LocationManager locationManager;

    DistanceTravelBinder mDistanceTravelBinder = new DistanceTravelBinder();
    static double distanceInMetres;
    static Location lastLocation = null;
    double lat,longt;

    public GetLocation(Context context ) {
        this.context=context;

    }

    @Override
    public void onCreate() {
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (lastLocation == null) {
                    lastLocation = location;
                }
                lat = location.getLatitude();
                longt = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
                alertDialog.setTitle("Enable Location");
                alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
                alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.cancel();
                    }
                });
                AlertDialog alert=alertDialog.create();
                alert.show();

            }
        };

        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000,
                1,
                locationListener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mDistanceTravelBinder;
    }

    public class DistanceTravelBinder extends Binder {
        GetLocation getBinder(){
            return GetLocation.this;
        }
    }

    public double getDistanceTraveled(){

        return distanceInMetres;
    }
    public double getlat(){
        return lat;
    }

    public double getlongt(){
        return longt;
    }

}