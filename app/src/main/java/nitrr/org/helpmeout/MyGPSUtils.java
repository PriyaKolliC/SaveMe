package nitrr.org.helpmeout;
 
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class MyGPSUtils {
    public static Location getGPSLocation(Context ctx) {
        LocationManager locationManager;
        Location location=null;
        try {
            locationManager = (LocationManager) ctx
                    .getSystemService(ctx.LOCATION_SERVICE);
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);;
            if (!isGPSEnabled && !isNetworkEnabled) {
                Log.d(MySettings.PROJ_NAME, "no gps");
            } else {
                    if (isGPSEnabled) {
                           location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    }
                if (isNetworkEnabled) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
 
        } catch (SecurityException e) {
    		Log.e(MySettings.PROJ_NAME, "error", e);
        }
        return location;
    }
}