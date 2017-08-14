package nitrr.org.helpmeout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.LinkedList;

public class SearchCont extends AppCompatActivity {
    Activity act = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cont);
        final ListView lv1 = (ListView) findViewById(R.id.listView);
        final EditText et1 = (EditText) findViewById(R.id.editText);
        Button b1 = (Button) findViewById(R.id.btnDone);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i1 = new Intent(act, ListEmContActivity.class);
                startActivity(i1);
                act.finish();
            }
        });
        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv1 = (TextView) view;
                String s1 = tv1.getText().toString();
                final String name = s1.substring(0, s1.indexOf(" : "));
                final String pn = s1.substring(s1.indexOf(" : ")+3);
                AlertDialog.Builder ab1 = new AlertDialog.Builder(act);
                ab1.setMessage("R u sure, want to add " + name + " to emergancy list!");
                ab1.setTitle(MySettings.PROJ_NAME);
                ab1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
                        String sql = "insert into phone_book(user_name, phone_no) values (?, ?)";
                        Object oa[] = new Object[2];
                        oa[0]=name;
                        oa[1]=pn;
                        db.execSQL(sql, oa);
                        db.close();
                        Toast.makeText(act, "Done!!", Toast.LENGTH_SHORT).show();
                    }
                });
                ab1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                ab1.show();
                return false;
            }
        });
        et1.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LinkedList<String> res = new LinkedList<String>();
                String whr = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " like ? " ;
                whr = whr + " or " + ContactsContract.CommonDataKinds.Phone.NUMBER + " like ? ";
                String sa[] = new String[2];
                sa[0] = "%"+ et1.getText()+ "%";
                sa[1] = "%"+ et1.getText()+ "%";
                Cursor c1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, whr, sa, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int in1 = c1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int in2 = c1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                while(c1.moveToNext()){
                    String name = c1.getString(in1);
                    String pn = c1.getString(in2);
                    res.add(name + " : " + pn);
                }
                ArrayAdapter<String> aa = new ArrayAdapter<String>(act, android.R.layout.simple_list_item_1, res);
                lv1.setAdapter(aa);
            }
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
