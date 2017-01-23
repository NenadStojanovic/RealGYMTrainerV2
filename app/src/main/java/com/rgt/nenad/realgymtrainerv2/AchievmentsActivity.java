package com.rgt.nenad.realgymtrainerv2;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class AchievmentsActivity extends AppCompatActivity {

    ArrayList<Achievement> dostignuca = new ArrayList<Achievement>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievments);
        getSupportActionBar().hide();

        try {


            SQLiteDatabase db1;
            db1 = openOrCreateDatabase("rgtbaza", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            boolean pom2 = db1.enableWriteAheadLogging();

            Cursor c = db1.rawQuery("SELECT * FROM Dostignuca",null);


            c.moveToFirst();

            while (c.isAfterLast() == false) {
                Achievement a = new Achievement(c.getString(c.getColumnIndex("type")),c.getString(c.getColumnIndex("name")), c.getString(c.getColumnIndex("active")), c.getString(c.getColumnIndex("value")), c.getString(c.getColumnIndex("ID")));
                dostignuca.add(a);

                c.moveToNext();
            }
            db1.close();
        }
        catch (SQLException e){
            e.getMessage();
        }

        ListView lista = (ListView)findViewById(R.id.listViewAchievements);
        AchievementAdapter adapter = new AchievementAdapter(this, dostignuca );
        lista.setAdapter(adapter);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }
}
