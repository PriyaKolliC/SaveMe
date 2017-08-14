package nitrr.org.helpmeout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditEmCont extends AppCompatActivity {
    Activity act = this;
    EditText et1, et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_em_cont);
        et1 = (EditText) findViewById(R.id.editText);
        et2 = (EditText) findViewById(R.id.editText2);
        populateOldData();
        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                populateOldData();
            }
        });
        Button b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
                String sql = "update phone_book set user_name =?, phone_no =?" +
                        " where id = ? ";
                Object oa[] = new Object[3];
                oa[0]=et1.getText().toString();
                oa[1]=et2.getText().toString();
                oa[2]=getIntent().getStringExtra("selId");
                db.execSQL(sql, oa);
                db.close();
                Toast.makeText(act, "Done!!", Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(act, ListEmContActivity.class);
                startActivity(i1);
                act.finish();
            }
        });
    }
    private void populateOldData() {
        SQLiteDatabase db = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        String sql = "select * from phone_book where id = ? ";
        String sa[] = new String[1];
        sa[0]=getIntent().getStringExtra("selId");
        Cursor c1 = db.rawQuery(sql, sa);
        int in1 = c1.getColumnIndex("user_name");
        int in2 = c1.getColumnIndex("phone_no");
        if(c1.moveToNext()){
            String name = c1.getString(in1);
            String pn = c1.getString(in2);
            et1.setText(name);
            et2.setText(pn);
        }
    }
}
