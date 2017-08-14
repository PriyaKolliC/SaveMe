package nitrr.org.helpmeout;

import android.content.Context;
import android.location.Location;
import android.util.Log;

public class MyGPSUtil {
	public static String tag = "MyGPSUtil";	
	
	public static void getArialFromMe(Context ctx, String lat2, String long2){
		MyGPSUtils g1 = new MyGPSUtils();
		Location L1 = g1.getGPSLocation(ctx);
		Location L2 = new Location("");
		L2.setLatitude(Double.parseDouble(lat2));
		L2.setLongitude(Double.parseDouble(long2));
		double dist = L1.distanceTo(L2);
		String disStr = dist + " Meters";
		if(dist>500){
			dist = dist/1000;
			disStr = dist + " KM's";
		}
		Log.d("afr", "Areal Diatance :" + dist+"");		
	}
}
