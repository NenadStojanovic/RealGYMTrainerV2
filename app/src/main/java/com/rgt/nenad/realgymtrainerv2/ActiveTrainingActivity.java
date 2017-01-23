package com.rgt.nenad.realgymtrainerv2;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ActiveTrainingActivity extends AppCompatActivity {

    ArrayList<Vezba> vezbe = new ArrayList<Vezba>();
    String TABLE_NAME = "Trening";
    TextView textViewTime;
    final CounterClass timer = new CounterClass(5400000, 1000);
    String Id2="1";
    String TABLE_NAME2="Profil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_training);
        getSupportActionBar().hide();

        Typeface DigitalClock = Typeface.createFromAsset(getAssets(),"Fonts/DS-DIGIB.TTF");
        textViewTime = (TextView)findViewById(R.id.textViewTime);
        textViewTime.setText("01:30:00");
        textViewTime.setTypeface(DigitalClock);

        timer.start();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            vezbe = (ArrayList<Vezba>)getIntent().getSerializableExtra("Vezbe"); //Obtaining data
        }

        ListView lv = (ListView) findViewById(R.id.listView);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this, vezbe );
        lv.setAdapter(adapter);
    }

    public void FinnishBtn(View view){
        try {
            SQLiteDatabase db1;
            db1 = openOrCreateDatabase("rgtbaza", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            boolean pom2 = db1.enableWriteAheadLogging();

            SharedPreferences sp = getSharedPreferences("MyPrefs", this.MODE_PRIVATE);


            timer.cancel();
            int suma=0;
            for (Vezba vezba : vezbe) {


                int key = 0;

                key = sp.getInt(vezba.getNaziv().toString(), key);
                suma = suma+sp.getInt(vezba.getNaziv().toString()+"suma",0);
                if (key == 1) {
                    if (Double.parseDouble(vezba.getFirstSet()) <= 20) {
                        ContentValues cv = new ContentValues();
                        cv.put("TEZINA_1", Double.parseDouble(vezba.getFirstSet()) - 2.5);
                        cv.put("TEZINA_2", Double.parseDouble(vezba.getSecondSet()) - 2.5);
                        cv.put("TEZINA_3", Double.parseDouble(vezba.getThirdSet()) - 2.5);
                        cv.put("TEZINA_4", Double.parseDouble(vezba.getFourthSet()) - 2.5);
                        int pom = db1.update(TABLE_NAME, cv, "ID = ? ", new String[]{vezba.getID()});

                    }
                    else if (Double.parseDouble(vezba.getFirstSet()) > 20 && Double.parseDouble(vezba.getFirstSet()) <= 40) {
                        ContentValues cv = new ContentValues();
                        cv.put("TEZINA_1", Double.parseDouble(vezba.getFirstSet()) - 5);
                        cv.put("TEZINA_2", Double.parseDouble(vezba.getSecondSet()) - 5);
                        cv.put("TEZINA_3", Double.parseDouble(vezba.getThirdSet()) - 5);
                        cv.put("TEZINA_4", Double.parseDouble(vezba.getFourthSet()) - 5);
                        int pom = db1.update(TABLE_NAME, cv, "ID = ? ", new String[]{vezba.getID()});
                    }
                    else if (Double.parseDouble(vezba.getFirstSet()) > 40 && Double.parseDouble(vezba.getFirstSet()) <= 60) {
                        ContentValues cv = new ContentValues();
                        cv.put("TEZINA_1", Double.parseDouble(vezba.getFirstSet()) - 7.5);
                        cv.put("TEZINA_2", Double.parseDouble(vezba.getSecondSet()) - 7.5);
                        cv.put("TEZINA_3", Double.parseDouble(vezba.getThirdSet()) - 7.5);
                        cv.put("TEZINA_4", Double.parseDouble(vezba.getFourthSet()) - 7.5);
                        int pom = db1.update(TABLE_NAME, cv, "ID = ? ", new String[]{vezba.getID()});
                    }
                    else {
                        ContentValues cv = new ContentValues();
                        cv.put("TEZINA_1", Double.parseDouble(vezba.getFirstSet()) - 10);
                        cv.put("TEZINA_2", Double.parseDouble(vezba.getSecondSet()) - 10);
                        cv.put("TEZINA_3", Double.parseDouble(vezba.getThirdSet()) - 10);
                        cv.put("TEZINA_4", Double.parseDouble(vezba.getFourthSet()) - 10);
                        int pom = db1.update(TABLE_NAME, cv, "ID = ? ", new String[]{vezba.getID()});
                    }


                } else if (key == 2) {
                    if (Double.parseDouble(vezba.getFirstSet()) <= 20) {
                        ContentValues cv = new ContentValues();
                        cv.put("TEZINA_1", Double.parseDouble(vezba.getFirstSet()) + 2.5);
                        cv.put("TEZINA_2", Double.parseDouble(vezba.getSecondSet()) + 2.5);
                        cv.put("TEZINA_3", Double.parseDouble(vezba.getThirdSet()) + 2.5);
                        cv.put("TEZINA_4", Double.parseDouble(vezba.getFourthSet()) + 2.5);
                        int pom = db1.update(TABLE_NAME, cv, "ID = ? ", new String[]{vezba.getID()});
                    } else if (Double.parseDouble(vezba.getFirstSet()) > 20 && Double.parseDouble(vezba.getFirstSet()) <= 40) {
                        ContentValues cv = new ContentValues();
                        cv.put("TEZINA_1", Double.parseDouble(vezba.getFirstSet()) + 5);
                        cv.put("TEZINA_2", Double.parseDouble(vezba.getSecondSet()) + 5);
                        cv.put("TEZINA_3", Double.parseDouble(vezba.getThirdSet()) + 5);
                        cv.put("TEZINA_4", Double.parseDouble(vezba.getFourthSet()) + 5);
                        int pom = db1.update(TABLE_NAME, cv, "ID = ? ", new String[]{vezba.getID()});
                    } else if (Double.parseDouble(vezba.getFirstSet()) > 40 && Double.parseDouble(vezba.getFirstSet()) <= 60) {
                        ContentValues cv = new ContentValues();
                        cv.put("TEZINA_1", Double.parseDouble(vezba.getFirstSet()) + 7.5);
                        cv.put("TEZINA_2", Double.parseDouble(vezba.getSecondSet()) + 7.5);
                        cv.put("TEZINA_3", Double.parseDouble(vezba.getThirdSet()) + 7.5);
                        cv.put("TEZINA_4", Double.parseDouble(vezba.getFourthSet()) + 7.5);
                        int pom = db1.update(TABLE_NAME, cv, "ID = ? ", new String[]{vezba.getID()});
                    } else {
                        ContentValues cv = new ContentValues();
                        cv.put("TEZINA_1", Double.parseDouble(vezba.getFirstSet()) + 10);
                        cv.put("TEZINA_2", Double.parseDouble(vezba.getSecondSet()) + 10);
                        cv.put("TEZINA_3", Double.parseDouble(vezba.getThirdSet()) + 10);
                        cv.put("TEZINA_4", Double.parseDouble(vezba.getFourthSet()) + 10);
                        int pom = db1.update(TABLE_NAME, cv, "ID = ? ", new String[]{vezba.getID()});
                    }
                }

            }
            Cursor c= db1.rawQuery("SELECT * FROM " + "Profil" + " WHERE id = ?", new String[]{"1"});
            c.moveToFirst();
            String brTreninga = c.getString(c.getColumnIndex("BrojTreninga"));
            String tip =c.getString(c.getColumnIndex("TipTreninga"));
            int tipTr = Integer.parseInt(tip);
            int brTr= Integer.parseInt(brTreninga);
            brTr--;
            if(brTr==0){
                if(tipTr==1)
                    brTr=4;
                else if(tipTr==2)
                    brTr=3;
                else
                    brTr=5;
            }

            ContentValues cv2 = new ContentValues();
            cv2.put("BrojTreninga",brTr);

            int pom = db1.update(TABLE_NAME2, cv2, "ID = ? ", new String[] {Id2});
            suma=suma/vezbe.size();
            //Toast.makeText(this,"Valjda je dobro",Toast.LENGTH_LONG).show();
            sp.edit().clear().commit();
            db1.close();
            Intent intent;

            intent = new Intent(getApplicationContext(), PopUpActivity.class);
            intent.putExtra("suma",suma);

            startActivity(intent);
        }
        catch (SQLException e)
        {
            e.getMessage();
        }

        SharedPreferences sp = getSharedPreferences("AchievementPrefs", this.MODE_PRIVATE);
        int treiningNumber = sp.getInt("Training_Number", 0);
        treiningNumber++;
        ArrayList<Achievement> dostignuca=LoadLockedAchievements("2");
        for(Achievement a:dostignuca){
            if(Integer.parseInt(a.getValue())<= treiningNumber){
                ActivateAchievement(a.getId());
            }
        }
        float weights = sp.getFloat("Weights_Sum", 0);
        ArrayList<Achievement> dostignuca2=LoadLockedAchievements("3");
        for(Achievement a:dostignuca2){
            if(Float.parseFloat(a.getValue())<= weights){
                ActivateAchievement(a.getId());
            }
        }


    }

    //method that unlocks specific achievement
    private void ActivateAchievement(String id) {
        try {


            SQLiteDatabase db1;
            db1 = openOrCreateDatabase("rgtbaza", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            boolean pom1 = db1.enableWriteAheadLogging();
            ContentValues cv = new ContentValues();
            cv.put("active", 2);
            int pom2 = db1.update("Dostignuca", cv, "ID = ? ", new String[]{id});

            db1.close();
            SharedPreferences sp = getSharedPreferences("AchievementPrefs", this.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("New_Achievement", 1);
            editor.apply();
        }
        catch (SQLException e){
            e.getMessage();
        }
    }

    //metod for loading all locked training number achievements
    public ArrayList<Achievement> LoadLockedAchievements(String type){
        ArrayList<Achievement> dostignuca = new ArrayList<>();
        try {


            SQLiteDatabase db1;
            db1 = openOrCreateDatabase("rgtbaza", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            boolean pom2 = db1.enableWriteAheadLogging();

            Cursor c = db1.rawQuery("SELECT * FROM Dostignuca WHERE active = ? AND type = ?", new String[]{"0", type});


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

        return dostignuca;

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            System.out.println(hms);
            textViewTime.setText(hms);
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            textViewTime.setText("Completed.");
        }
    }

    //override BackButtonTransition
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


}
