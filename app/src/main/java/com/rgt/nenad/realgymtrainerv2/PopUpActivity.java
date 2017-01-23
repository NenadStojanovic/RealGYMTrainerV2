package com.rgt.nenad.realgymtrainerv2;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PopUpActivity extends AppCompatActivity {

    TextView textViewMain;
    TextView textViewOpis;
    TextView textViewProcenat;
    ImageView newAchievement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        getSupportActionBar().hide();
        textViewMain=(TextView)findViewById(R.id.TextViewMain);
        textViewOpis=(TextView)findViewById(R.id.textViewOpis);
        textViewProcenat=(TextView)findViewById(R.id.textViewProcenat);
        newAchievement = (ImageView)findViewById(R.id.imageViewNewAch);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.9),(int)(height*0.9));

        Intent intent = getIntent();
        int suma = intent.getIntExtra("suma",0);
        if(suma<80)
        {
            textViewMain.setText(getString(R.string.Dont_worry));
            textViewMain.setTextColor(getResources().getColor(R.color.FontYellow));
            textViewOpis.setText(getString(R.string.Dont_worry_long));
            textViewProcenat.setText(Integer.toString(suma)+"%");
            textViewProcenat.setTextColor(getResources().getColor(R.color.FontYellow));
        }
        else if(suma<90)
        {
            textViewMain.setText(getString(R.string.good_job));
            textViewMain.setTextColor(getResources().getColor(R.color.FontGreen));
            textViewOpis.setText(getString(R.string.good_job_long));
            textViewProcenat.setText(Integer.toString(suma)+"%");
            textViewProcenat.setTextColor(getResources().getColor(R.color.FontGreen));

        }
        else{

            textViewMain.setText(getString(R.string.excellent));
            textViewMain.setTextColor(getResources().getColor(R.color.colorPrimary));
            textViewOpis.setText(getString(R.string.excellent_logng));
            textViewProcenat.setText(Integer.toString(suma)+"%");
            textViewProcenat.setTextColor(getResources().getColor(R.color.colorPrimary));

        }

        SharedPreferences sp = getSharedPreferences("AchievementPrefs", this.MODE_PRIVATE);
        int new_achievement = sp.getInt("New_Achievement", 0);
        if(new_achievement==0)
        {
            newAchievement.setImageResource(R.drawable.achiventeibbonwhite);
        }
        else
        {
            newAchievement.setImageResource(R.drawable.achiventribbongreen);
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("New_Achievement", 0);
            editor.apply();
        }

    }

    public void OKbtn(View view) {


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }



    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }


}
