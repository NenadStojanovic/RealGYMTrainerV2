package com.rgt.nenad.realgymtrainerv2;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hiding ActionBar
        getSupportActionBar().hide();

        //Setting text into Quotes TextView
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "Fonts/veteran_typewriter.ttf");
        TextView tv = (TextView) findViewById(R.id.textViewQuotes);
        tv.setTypeface(tf);
        tv.setText("It's simple. If it jiggles, it's \nFAT! \n   ...Arnold Schwarzenegger");


    }
}
