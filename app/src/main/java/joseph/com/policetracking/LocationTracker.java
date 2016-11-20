package joseph.com.policetracking;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by Brian on 11/19/2016.
 */

public class LocationTracker {

    Context mContext;
    final int locationPermission = 9999;

    public static double currentLocationCoordinates[];



    public LocationTracker(Context context) {
        this.mContext = context;
    }

    public void startTrackingLocation() {

        if (ActivityCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity)mContext,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    locationPermission);
            return;
        } else {
            System.out.println("Location permission possessed.");
        }

        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        double lastknownLocationArray[] = {
                lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()};
        currentLocationCoordinates = lastknownLocationArray;
       System.out.println("last known location: " + currentLocationCoordinates[0] + "," + currentLocationCoordinates[1]);

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double coord[] = {location.getLatitude(), location.getLongitude()};

                currentLocationCoordinates = coord;
                System.out.println("current location: " + currentLocationCoordinates[0] + "," + currentLocationCoordinates[1]);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
            }

            @Override
            public void onProviderEnabled(String s) {
            }

            @Override
            public void onProviderDisabled(String s) {
            }
        };



        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case locationPermission: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {
                    Toast.makeText(mContext, "In order to use this service, you must enable access to location", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }



}
