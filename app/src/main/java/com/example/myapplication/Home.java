package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    ListView lst1;
    ArrayList<String> titles = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.activity_home);

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddWebsiteActivity.class);
                view.getContext().startActivity(intent);}
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this.getRecords();
            }



            private void getRecords() {
                SQLiteDatabase db = openOrCreateDatabase("WebsiteDB", Context.MODE_PRIVATE, null);

                lst1 = findViewById(R.id.lvRssHome);
                registerForContextMenu(lst1);

                final Cursor c = db.rawQuery("select * from records", null);
                int id = c.getColumnIndex("id");
                int name = c.getColumnIndex("name");
                int address = c.getColumnIndex("address");
                titles.clear();


                arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, titles);
                lst1.setAdapter(arrayAdapter);

                final ArrayList<WebsitesInformation> stud = new ArrayList<WebsitesInformation>();


                if (c.moveToFirst()) {
                    do {
                        WebsitesInformation stu = new WebsitesInformation();
                        stu.id = c.getInt(id);
                        stu.name = c.getString(name);
                        stu.address = c.getString(address);
                        stud.add(stu);

                        titles.add(c.getString(id) + " \t" + c.getString(name));

                    } while (c.moveToNext());
                    arrayAdapter.notifyDataSetChanged();
                    lst1.invalidateViews();


                }

                lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(i);

                    }
                });
            }


        });

    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu,v,menuInfo);
        menu.add(0,v.getId(),0,"Delete");

    }

    public boolean onContextItemSelected(MenuItem item)
    {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(item.getTitle() == "Delete")
        {
//            titles.remove(info.position);
            Delete();
        }

        return true;
    }

    public void Delete()
    {
        try
        {
            String id = "1";

            SQLiteDatabase db = openOrCreateDatabase("WebSiteDB",Context.MODE_PRIVATE,null);

            Log.i("ttttttttttttttt", id);

            String sql = "delete from records where id = ?";
            SQLiteStatement statement = db.compileStatement(sql);

            statement.bindString(1, id);
            statement.execute();
            Toast.makeText(this,"Record Deleted",Toast.LENGTH_LONG).show();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Record Fail",Toast.LENGTH_LONG).show();
        }
    }


}
