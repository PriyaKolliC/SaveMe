package nitrr.org.helpmeout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MyReceiver extends BroadcastReceiver {
    Context ctx;
    int c = 0;
    int msgCount=0;
    long timeInMilis = -1;

    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            ctx = context;
            c++;
            if (timeInMilis != -1 && System.currentTimeMillis() - timeInMilis > MySettings.CLICK_TIME_GAP) {
                c = 1;
            }
            timeInMilis = System.currentTimeMillis();
            if (c >= MySettings.CLICK_TRIGGER_COUNT) {
                sendMsg();
                msgCount=1;
                final Timer t1 = new Timer();
                TimerTask tt = new TimerTask() {
                    public void run() {
                        sendMsg();
                        msgCount++;
                        if(msgCount>=3){
                            t1.cancel();
                            t1.purge();
                        }
                    }
                };
                t1.scheduleAtFixedRate(tt,1000*60*3, 1000*60*3);
            }
        } catch (Exception e) {
            Log.e(MySettings.PROJ_NAME, "error", e);
        }
    }

    private void sendMsg() {
        LinkedList<String> pNoList = getPhonNoList();
        Location loc = getLocation();
        for(String no : pNoList){
            SmsManager sm = SmsManager.getDefault();
            String msg=  MySettings.msg;
            if(loc!=null) {
                msg = msg + ", my location is " + getLocationLink(loc);
            }
            sm.sendTextMessage(no, null, msg, null, null);
        }
        c = 1;
        Log.d("afroz", c + "");
    }

    private LinkedList<String> getPhonNoList() {
        LinkedList<String> res = new LinkedList<String>();
        SQLiteDatabase db = ctx.openOrCreateDatabase("mydb", ctx.MODE_PRIVATE, null);
        String sql = "select * from phone_book order by user_name ";
        String sa[] = {};
        Cursor c1 = db.rawQuery(sql, sa);
        int in2 = c1.getColumnIndex("phone_no");
        int i = 0;
        while (c1.moveToNext()) {
            String pn = c1.getString(in2);
            res.add(pn);
        }
        db.close();
        return res;
    }

    private String getUserName() {
        String res = "";
        return res;
    }



    private Location getLocation(){
        Location loc = null;
        if(MyService.gpsTrk.lastTimeLocationChanged-System.currentTimeMillis()>1000*60*2){
            Location tLoc=MyGPSUtils.getGPSLocation(ctx);
            if(tLoc!=null){
                loc=MyGPSUtils.getGPSLocation(ctx);
            }
            else {
                loc=MyService.gpsTrk.location;
            }
        }
        else {
            if(MyService.gpsTrk.location!=null){
                loc=MyService.gpsTrk.location;
            }
        }
        return loc;
    }
    private String getLocationLink(Location loc) {
        Location lo1 =MyGPSUtils.getGPSLocation(ctx);
        double lat = loc.getLatitude();
        double longitude = loc.getLongitude();
        //String res = "https://www.google.co.in/maps/@" + lat + "," + longitude + ",16z";
        String res = "https://www.google.com/maps?q=loc:" + lat + "," + longitude;
        return res;
    }
    private String getLocationName(Location loc) {
        String res ="";
        try {
            Geocoder gcd = new Geocoder(ctx, Locale.getDefault());
            List<Address> addresses;
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                res = addresses.get(0).getLocality() + " :: " + addresses.get(0).getSubLocality();
            }
        } catch (Exception e) {
            Log.e(MySettings.PROJ_NAME, "error", e);
        }
        return  res;
    }
}