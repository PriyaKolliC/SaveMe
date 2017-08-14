package nitrr.org.helpmeout;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class MyService extends Service {
    static MyReceiver r1=null;
    static GPSTracker1 gpsTrk=null;
    public MyService() {
    }
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(r1==null){
            r1 = new MyReceiver();
            gpsTrk=new GPSTracker1(MyService.this);
            IntentFilter inf = new IntentFilter();
            inf.addAction("android.intent.action.SCREEN_ON");
            inf.addAction("android.intent.action.SCREEN_OFF");
            registerReceiver(r1, inf);
        }
    }

    @Override
    public void onDestroy() {
        if(r1!=null){
            unregisterReceiver(r1);
            r1=null;
            gpsTrk.stopUsingGPS();
            gpsTrk=null;
        }
        super.onDestroy();
    }
}
