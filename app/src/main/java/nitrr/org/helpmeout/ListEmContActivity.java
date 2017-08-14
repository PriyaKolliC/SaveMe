package nitrr.org.helpmeout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;

public class ListEmContActivity extends AppCompatActivity {
    String selId = null;
    EditText et3;
    ListView lv1;
    HashMap<Integer, String> m1 = new HashMap<Integer, String>();
    Activity act = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_em_cont);
        lv1 = (ListView) findViewById(R.id.listView);
        registerForContextMenu(lv1);
        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selId= m1.get(position);
                return false;
            }
        });
        Button b1 = (Button) findViewById(R.id.addEmCont);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i1 = new Intent(act, SearchCont.class);
                startActivity(i1);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }
    private void refreshList() {
        LinkedList<String> res = new LinkedList<String>();
        SQLiteDatabase db = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
        String sql = "select * from phone_book order by user_name ";
        String sa[] = {};
        Cursor c1 = db.rawQuery(sql, sa);
        int in1 = c1.getColumnIndex("user_name");
        int in2 = c1.getColumnIndex("phone_no");
        int in3 = c1.getColumnIndex("id");
        int i = 0;
        m1.clear();
        while(c1.moveToNext()){
            String name = c1.getString(in1);
            String pn = c1.getString(in2);
            String id = c1.getString(in3);
            res.add(name + " : " + pn);
            m1.put(i++, id);
        }
        db.close();
        ArrayAdapter<String> aa = new ArrayAdapter<String>(act, android.R.layout.simple_list_item_1, res);
        lv1.setAdapter(aa);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mf = getMenuInflater();
        mf.inflate(R.menu.mymenu2, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mnuDel){
            AlertDialog.Builder ab1 = new AlertDialog.Builder(act);
            ab1.setMessage("R u sure, want to delete!");
            ab1.setTitle(MySettings.PROJ_NAME);
            ab1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SQLiteDatabase db = openOrCreateDatabase("mydb", MODE_PRIVATE, null);
                    String sql = "delete from phone_book  where id = ? ";
                    Object oa[] = new Object[1];
                    oa[0]=selId;
                    db.execSQL(sql, oa);
                    db.close();
                    refreshList();
                    Toast.makeText(act, "Done!!", Toast.LENGTH_SHORT).show();
                }
            });
            ab1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            ab1.show();
        }
        else {
            Intent i1 = new Intent(act, EditEmCont.class);
            i1.putExtra("selId", selId);
            startActivity(i1);
        }
        return super.onContextItemSelected(item);
    }
}
