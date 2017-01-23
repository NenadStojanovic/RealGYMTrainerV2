package com.rgt.nenad.realgymtrainerv2;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.app.ActivityManager.*;

public class MainActivity extends AppCompatActivity {

    String lineIWant;
    String nameIWant;
    int LineCount = 27;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        sp.edit().clear().commit();
        //Hiding ActionBar
        getSupportActionBar().hide();

        //Setting text and font into Quotes TextViews
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "Fonts/veteran_typewriter.ttf");
        TextView tv = (TextView) findViewById(R.id.textViewQuotes);
        tv.setTypeface(tf);



        TextView tv2 = (TextView) findViewById(R.id.textViewNames);
        tv2.setTypeface(tf);

        //selecting random quotes
        int RandIndex=(int)Math.ceil(Math.random()*(LineCount-1));
        if(RandIndex % 2 != 0)
        {
            RandIndex++;
        }

        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("motivational_quotes.txt")));
            for(int i = 0; i < RandIndex; ++i)
                br.readLine();

             lineIWant = br.readLine();
            nameIWant = br.readLine();
           br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tv.setText(lineIWant);
        tv2.setText(nameIWant);

        SharedPreferences sp2 = getSharedPreferences("First_start_pref", Activity.MODE_PRIVATE);
        int first_start = sp2.getInt("First_start",0);
        if(first_start==0)
        {
            Intent intent = new Intent(getApplicationContext(), StartPopUpScreen.class);
            startActivity(intent);
        }

    }
    @Override
    protected void onDestroy() {
        // closing Entire Application
        android.os.Process.killProcess(android.os.Process.myPid());
       // ActivityManager.killBackgroundProcesses(getApplicationContext().getPackageName());
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // .... other stuff in my onResume ....
        this.doubleBackToExitPressedOnce = false;
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());

            System.exit(0);

            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    //funkcija za dugme za otvaranje treninga
    public void openTrening(View view)
    {
        Intent intent = new Intent(getApplicationContext(), TreningActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    //funkcija za dugme za otvaranje profila
    public void openProfil(View view)
    {
        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
