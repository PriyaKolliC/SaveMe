package nitrr.org.helpmeout;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChkPermiAct extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    Activity act = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chk_permi);
        Button b1 = (Button) findViewById(R.id.btnChkPer);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermission(Manifest.permission.SEND_SMS, "This is required to send SMS at the time of danger!");
                getPermission(Manifest.permission.READ_CONTACTS, "This is required to select emergency contact!");
                getPermission(Manifest.permission.ACCESS_FINE_LOCATION, "This is required to to get your location at the time of danger!");
            }
        });
    }
    public void getPermission(String per, String reason){
        if (ActivityCompat.checkSelfPermission(act, per) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(act, per)) {
                if(reason!=null) {
                    Toast.makeText(act, reason, Toast.LENGTH_SHORT).show();
                }
            }
            AlertDialog.Builder ab1 = new AlertDialog.Builder(act);
            ab1.setMessage("Please remember to select 'Remember my decision or always'!");
            ab1.setTitle(MySettings.PROJ_NAME);
            ab1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
           // ab1.show();
           ActivityCompat.requestPermissions(act,
                    new String[]{per},
                    0);
        } else {
            Toast.makeText(act, "Permission already granted! for " + per, Toast.LENGTH_SHORT).show();
        }
    }
}
