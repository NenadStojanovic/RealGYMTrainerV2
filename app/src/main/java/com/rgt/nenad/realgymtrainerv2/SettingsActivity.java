package com.rgt.nenad.realgymtrainerv2;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    ArrayList<SettingsItem> lista = new ArrayList<SettingsItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        try {


            SQLiteDatabase db1;
            db1 = openOrCreateDatabase("rgtbaza", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            boolean pom2 = db1.enableWriteAheadLogging();

            Cursor c = db1.rawQuery("SELECT * FROM Trening",null);


            c.moveToFirst();

            while (c.isAfterLast() == false) {
                SettingsItem a = new SettingsItem(c.getString(c.getColumnIndex("NAZIV")),c.getString(c.getColumnIndex("ID")), c.getString(c.getColumnIndex("active")));
                lista.add(a);

                c.moveToNext();
            }
            db1.close();
        }
        catch (SQLException e){
            e.getMessage();
        }

        ListView lv = (ListView)findViewById(R.id.listViewSettings);
        SettingsAdapter adapter = new SettingsAdapter(this, lista);
        lv.setAdapter(adapter);
    }

    //override BackButtonTransition
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }

    public void openAddNew(View view){
        Intent intent = new Intent(getApplicationContext(), AddNewActivity.class);
        startActivity(intent);
       // overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);

    }


}
