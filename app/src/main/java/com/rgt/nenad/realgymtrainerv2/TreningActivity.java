package com.rgt.nenad.realgymtrainerv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TreningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trening);


    }


    //override BackButtonTransition
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
