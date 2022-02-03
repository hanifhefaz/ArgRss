package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddWebsiteActivity extends AppCompatActivity {
    EditText ed1,ed2;
    Button b1;
    boolean isAllFieldsChecked = false;

    ListView lst1;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_website);

        ed1 = findViewById(R.id.editText1);
        ed2 = findViewById(R.id.editText2);
        b1 = findViewById(R.id.buttonaddweb);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
    }
//    public void getRecords() {
//        SQLiteDatabase db = openOrCreateDatabase("WebsiteDB", Context.MODE_PRIVATE, null);
//
//        lst1 = findViewById(R.id.lvRssHome);
//        final Cursor c = db.rawQuery("select * from records", null);
//        int id = c.getColumnIndex("id");
//        int name = c.getColumnIndex("name");
//        int address = c.getColumnIndex("address");
//        titles.clear();
//
//
//        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, titles);
//        lst1.setAdapter(arrayAdapter);
//
//        final ArrayList<WebsitesInformation> stud = new ArrayList<WebsitesInformation>();
//
//
//        if (c.moveToFirst()) {
//            do {
//                WebsitesInformation stu = new WebsitesInformation();
//                stu.id = c.getInt(id);
//                stu.name = c.getString(name);
//                stu.address = c.getString(address);
//                stud.add(stu);
//
//                titles.add(c.getString(id) + " \t " + c.getString(name) + " \n " + c.getString(address));
//
//            } while (c.moveToNext());
//            arrayAdapter.notifyDataSetChanged();
//            lst1.invalidateViews();
//
//
//        }
//
//        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(i);
//
//            }
//        });
//    }

    public void insert()
    {
        isAllFieldsChecked = CheckAllFields();
        try
        {
            if (isAllFieldsChecked) {
                String name = ed1.getText().toString();
                String address = ed2.getText().toString();

                SQLiteDatabase db = openOrCreateDatabase("WebsiteDB", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS records(id INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR,address VARCHAR)");

                String sql = "insert into records(name,address)values(?,?)";
                SQLiteStatement statement = db.compileStatement(sql);
                statement.bindString(1, name);
                statement.bindString(2, address);
                statement.execute();
                Toast.makeText(this, "Record added", Toast.LENGTH_SHORT).show();
//                getRecords();
                ed1.setText("");
                ed2.setText("");
                ed1.requestFocus();
                Intent i = new Intent(getApplicationContext(), Home.class);
                startActivity(i);
                finish();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Record Fail",Toast.LENGTH_LONG).show();
        }


    }

    private boolean CheckAllFields() {
        if (ed1.length() == 0) {
            ed1.setError("Can not be empty!");
            return false;
        }

        if (ed2.length() == 0) {
            ed2.setError("This field is required");
            return false;
        }

        // after all validation return true.
        return true;
    }
}
