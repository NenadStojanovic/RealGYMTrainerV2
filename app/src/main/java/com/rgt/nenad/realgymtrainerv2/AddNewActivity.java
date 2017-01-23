package com.rgt.nenad.realgymtrainerv2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.SQLException;

public class AddNewActivity extends AppCompatActivity {

    RadioGroup rg1;
    RadioGroup rg2;
    RadioButton chest;
    RadioButton biceps;
    RadioButton shoulders;
    RadioButton triceps;
    RadioButton back;
    RadioButton legs;

    EditText name;
    EditText weight1;
    EditText weight2;
    EditText weight3;
    EditText weight4;

    CheckBox set1;
    CheckBox set2;
    CheckBox set3;
    CheckBox set4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        getSupportActionBar().hide();

        rg1 = (RadioGroup) findViewById(R.id.RadioGroupPrva);
        rg2 = (RadioGroup) findViewById(R.id.RadioGroupDruga);
        rg1.clearCheck(); // this is so we can start fresh, with no selection on both RadioGroups
        rg2.clearCheck();
        rg1.setOnCheckedChangeListener(listener1);
        rg2.setOnCheckedChangeListener(listener2);

        chest = (RadioButton) findViewById(R.id.radioButton);
        biceps = (RadioButton) findViewById(R.id.radioButton2);
        shoulders = (RadioButton) findViewById(R.id.radioButton3);
        triceps = (RadioButton) findViewById(R.id.radioButton4);
        back = (RadioButton) findViewById(R.id.radioButton5);
        legs = (RadioButton) findViewById(R.id.radioButton6);

        name = (EditText) findViewById(R.id.editTextExcName);
        weight1 = (EditText) findViewById(R.id.editTextset1);
        weight2 = (EditText) findViewById(R.id.editTextset2);
        weight3 = (EditText) findViewById(R.id.editTextset3);
        weight4 = (EditText) findViewById(R.id.editTextset4);

        set1 = (CheckBox) findViewById(R.id.checkBoxSet1);
        set2 = (CheckBox) findViewById(R.id.checkBoxSet2);
        set3 = (CheckBox) findViewById(R.id.checkBoxSet3);
        set4 = (CheckBox) findViewById(R.id.checkBoxSet4);




    }

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg2.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                rg2.clearCheck(); // clear the second RadioGroup!
                rg2.setOnCheckedChangeListener(listener2); //reset the listener
                Log.e("XXX2", "do the work");
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId != -1) {
                rg1.setOnCheckedChangeListener(null);
                rg1.clearCheck();
                rg1.setOnCheckedChangeListener(listener1);
                Log.e("XXX2", "do the work");
            }
        }
    };

    public void SaveExercise(View view){
        try{
            SQLiteDatabase db1;
            db1 = openOrCreateDatabase("rgtbaza", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            boolean pom2 = db1.enableWriteAheadLogging();
            ContentValues cv = new ContentValues();
            String pomstr = name.getText().toString();
            if("".equals(name.getText().toString()))
            {
                Toast.makeText(this, "Please insert exercise name", Toast.LENGTH_SHORT).show();
                return;
            }
            cv.put("NAZIV",name.getText().toString());

            if(chest.isChecked())
                cv.put("TIP_VEZBE","Chest");
            else if(biceps.isChecked())
                cv.put("TIP_VEZBE","Biceps");
            else if(shoulders.isChecked())
                cv.put("TIP_VEZBE","Shoulders");
            else if(triceps.isChecked())
                cv.put("TIP_VEZBE","Triceps");
            else if(back.isChecked())
                cv.put("TIP_VEZBE","Back");
            else if(legs.isChecked())
                cv.put("TIP_VEZBE","Legs");
            else{
                Toast.makeText(this, "Please choose exercise type", Toast.LENGTH_SHORT).show();
                return;
            }
            if(set1.isChecked())
                cv.put("SET_1","1");
            if(set2.isChecked())
                cv.put("SET_2","1");
            if(set3.isChecked())
                cv.put("SET_3","1");
            if(set4.isChecked())
                cv.put("SET_4","1");
            boolean pom1 = set1.isChecked();
            boolean pom22= set2.isChecked();
            boolean pom3 = set3.isChecked();
            boolean pom4 = set4.isChecked();
            if(!(set1.isChecked())&&!(set2.isChecked())&&!(set3.isChecked())&&!(set4.isChecked())){
                Toast.makeText(this, "Please choose sets for exercise", Toast.LENGTH_SHORT).show();
                return;
            }
            cv.put("TEZINA_1",weight1.getText().toString());
            cv.put("TEZINA_2",weight2.getText().toString());
            cv.put("TEZINA_3",weight3.getText().toString());
            cv.put("TEZINA_4",weight4.getText().toString());
            cv.put("BR_OPIS","-1");
            db1.insert("Trening", null, cv);
            db1.close();
            Toast.makeText(this, "Exercise is saved!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);

        }
        catch (android.database.SQLException ex){
            ex.getMessage();
        }




    }

    public void DiscardBtn(View view){
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }
}
