package nitrr.org.helpmeout;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

public class MyPermissionUtil extends Activity implements ActivityCompat.OnRequestPermissionsResultCallback{
    Runnable r1;
    Activity act;
    public void runWithPermission(Activity act, String per, Runnable r1, String reason){
        this.r1=r1;
        this.act=act;
        if (ActivityCompat.checkSelfPermission(act, per) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(act, per)) {
                if(reason!=null) {
                    Toast.makeText(act, reason, Toast.LENGTH_SHORT).show();
                }
            }
            ActivityCompat.requestPermissions(act,
                    new String[]{per},
                    0);
        } else {
            r1.run();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                r1.run();
            }
        }
    }
}
