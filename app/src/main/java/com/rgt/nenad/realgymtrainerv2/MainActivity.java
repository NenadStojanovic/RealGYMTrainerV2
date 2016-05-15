package com.rgt.nenad.realgymtrainerv2;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    String lineIWant;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "motivational_quotes.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hiding ActionBar
        getSupportActionBar().hide();

        //Setting text into Quotes TextViews
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "Fonts/veteran_typewriter.ttf");
        TextView tv = (TextView) findViewById(R.id.textViewQuotes);
        tv.setTypeface(tf);
        char[] lineWant;


        TextView tv2 = (TextView) findViewById(R.id.textViewNames);
        tv2.setTypeface(tf);
        tv2.setText("...Arnold Schwarzenegger");


        AssetManager assetManager = getAssets();



        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("motivational_quotes.txt")));
            for(int i = 0; i < 3; ++i)
                br.readLine();

            String lineIWant = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        tv.setText(lineIWant);


    }
}
