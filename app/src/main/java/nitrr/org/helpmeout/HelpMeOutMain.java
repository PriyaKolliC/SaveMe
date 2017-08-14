package nitrr.org.helpmeout;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class HelpMeOutMain extends AppCompatActivity {
    Activity act = this;
    static boolean isOn = false;
    ImageView imgOnOff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_me_out_main);
        imgOnOff = (ImageView) findViewById(R.id.imgOnOff);
        refreshImg();
        imgOnOff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isOn==false){
                    Intent i1 = new Intent(act, MyService.class);
                    startService(i1);
                }
                else {
                    Intent i1 = new Intent(act, MyService.class);
                    stopService(i1);
                }
                isOn=!isOn;
                refreshImg();
            }
        });
    }

    private void refreshImg() {
        if(isOn){
            imgOnOff.setImageResource(R.drawable.off);
        }
        else {
            imgOnOff.setImageResource(R.drawable.on);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mf = getMenuInflater();
        mf.inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mnuSetting){
        }
        else if(item.getItemId()==R.id.mnuShowList){
            Intent i1 = new Intent(act, ListEmContActivity.class);
            startActivity(i1);
        }
        else if(item.getItemId()==R.id.mnuChkPer){
            Intent i1 = new Intent(act, ChkPermiAct.class);
            startActivity(i1);
        }
        return super.onOptionsItemSelected(item);
    }
}
