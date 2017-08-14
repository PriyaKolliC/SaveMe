package nitrr.org.helpmeout;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class HelpMeOut extends AppCompatActivity {
    Activity act = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timer t1 = new Timer();
        TimerTask tt = new TimerTask() {
            public void run() {
                Intent i1 = new Intent(act, HelpMeOutMain.class);
                startActivity(i1);
                act.finish();
            }
        };
        SQLiteDatabase db = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        String sql = "create table if not exists phone_book( id INTEGER primary key, " +
                " user_name varchar[50], phone_no varchar[15])";
        db.execSQL(sql);
        db.close();
        t1.schedule(tt, 1500);
    }
}
