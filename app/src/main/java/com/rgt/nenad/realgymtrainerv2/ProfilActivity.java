package com.rgt.nenad.realgymtrainerv2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

public class ProfilActivity extends AppCompatActivity {


    TextView Ime;
    TextView Prezime;
    TextView Visina;
    TextView Tezina;
    TextView BMI;
    TextView Ruke;
    TextView Grudi;
    TextView Struk;
    TextView Noge;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //Hiding ActionBar
        getSupportActionBar().hide();

        //Oppening database

        DBMain db;
        db = new DBMain(this);

        try {

            db.createDB();
        } catch (IOException ioe) {

            throw new Error("Database not created....");
        }

        try {
            db.openDB();

        }catch(Exception sqle){

            throw sqle;
        }

        //Working datavase variable
        SQLiteDatabase db1;
        db1=openOrCreateDatabase("rgtbaza",SQLiteDatabase.CREATE_IF_NECESSARY,null);
        //SQLiteDatabase db1 =  getWritableDatabase();
        Cursor c= db1.rawQuery("SELECT * FROM " + "Profil" + " WHERE id = ?", new String[] {"1"});

        this.Ime = (TextView) findViewById(R.id.textViewIme);
        this.Visina= (TextView) findViewById(R.id.textViewHeightValue);
        this.Tezina =  (TextView) findViewById(R.id.textViewWeightVal);
        this.BMI = (TextView) findViewById(R.id.textViewBMIValue);
        this.Ruke = (TextView) findViewById(R.id.textViewRuke);
        this.Grudi = (TextView) findViewById(R.id.textViewGrudi);
        this.Struk = (TextView) findViewById(R.id.textViewStruk);
        this.Noge = (TextView) findViewById(R.id.textViewNoga);

        /*this.Ime.setText(db.vratiVrednost(1, this) + " " + db.vratiVrednost(2,this));
        this.Visina.setText(db.vratiVrednost(8,this));
        this.Tezina.setText(db.vratiVrednost(7,this));
        this.BMI.setText(db.vratiVrednost(9,this));
        this.Ruke.setText(db.vratiVrednost(3,this));
        this.Grudi.setText(db.vratiVrednost(5,this));
        this.Struk.setText(db.vratiVrednost(4,this));
        this.Noge.setText(db.vratiVrednost(6,this));*/

        try {
            c.moveToFirst();
            this.Ime.setText(c.getString(c.getColumnIndex("Ime")) + " " + c.getString(c.getColumnIndex("Prezime")));
            this.Visina.setText(c.getString(c.getColumnIndex("Visina")));
            this.Tezina.setText(c.getString(7));
            this.BMI.setText(c.getString(9));
            this.Ruke.setText(c.getString(3));
            this.Grudi.setText(c.getString(5));
            this.Struk.setText(c.getString(4));
            this.Noge.setText(c.getString(6));
        }
        catch (Exception e) {
                e.getMessage();
        }



    }


    //override BackButtonTransition
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    //funkcija za dugme za otvaranje treninga
    public void openEdit(View view)
    {
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
