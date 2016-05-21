package com.rgt.nenad.realgymtrainerv2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class EditActivity extends AppCompatActivity {

    EditText Ime;
    EditText Prezime;
    EditText Visina;
    EditText Tezina;
    EditText Ruke;
    EditText Grudi;
    EditText Struk;
    EditText Noge;
    String DB_MAME = "rgtbaza";
    String Id = "1";
    String TABLE_NAME = "Profil";
    DBMain db;

    int NapRuke;
    int NapGrudi;
    int NapStruk;
    int NapNoge;
    int NapVisina;
    int NapTezina;
    int NapBMI;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);



        //Hiding ActionBar
        getSupportActionBar().hide();

        Ime = (EditText) findViewById(R.id.editTextIme);
        Prezime = (EditText)  findViewById(R.id.editTextPrezime);
        Visina = (EditText)  findViewById(R.id.editTextVisina);
        Tezina = (EditText) findViewById(R.id.editTextTezina);
        Ruke = (EditText)  findViewById(R.id.editTextRuka);
        Grudi = (EditText)  findViewById(R.id.editTextGrudi);
        Struk = (EditText)  findViewById(R.id.editTextStruk);
        Noge = (EditText) findViewById(R.id.editTextNoga);

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

        try {
            c.moveToFirst();
            this.Ime.setText(c.getString(c.getColumnIndex("Ime")));
            this.Prezime.setText(c.getString(c.getColumnIndex("Prezime")));
            this.Visina.setText(c.getString(c.getColumnIndex("Visina")));
            this.Tezina.setText(c.getString(7));

            this.Ruke.setText(c.getString(3));
            this.Grudi.setText(c.getString(5));
            this.Struk.setText(c.getString(4));
            this.Noge.setText(c.getString(6));

            this.NapBMI=Integer.parseInt(c.getString(c.getColumnIndex("NapBMI")));
            this.NapGrudi=Integer.parseInt(c.getString(c.getColumnIndex("NapGrudi")));
            this.NapNoge=Integer.parseInt(c.getString(c.getColumnIndex("NapNoge")));
            this.NapRuke=Integer.parseInt(c.getString(c.getColumnIndex("NapRuke")));
            this.NapStruk=Integer.parseInt(c.getString(c.getColumnIndex("NapStruk")));
            this.NapTezina=Integer.parseInt(c.getString(c.getColumnIndex("NapTezina")));
            this.NapVisina=Integer.parseInt(c.getString(c.getColumnIndex("NapVisina")));

        }
        catch (Exception e) {
            e.getMessage();
        }
        db1.close();


    }



    //override BackButtonTransition
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void radioBtnClicked(View view){

    }

    public void SaveProfile(View view){


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
        try {
            SQLiteDatabase db1;
            db1 = openOrCreateDatabase("rgtbaza", SQLiteDatabase.CREATE_IF_NECESSARY, null);

            boolean pom2 = db1.enableWriteAheadLogging();
            Cursor c= db1.rawQuery("SELECT * FROM " + "Profil" + " WHERE id = ?", new String[] {"1"});


            c.moveToFirst();
            this.NapGrudi= this.NapGrudi + Integer.parseInt(this.Grudi.getText().toString()) - Integer.parseInt((c.getString(c.getColumnIndex("ObimGrudi"))));
            this.NapVisina=this.NapVisina + Integer.parseInt(this.Visina.getText().toString()) - Integer.parseInt((c.getString(c.getColumnIndex("Visina"))));
            this.NapTezina= this.NapTezina + Integer.parseInt(this.Tezina.getText().toString()) - Integer.parseInt((c.getString(c.getColumnIndex("Tezina"))));
            this.NapRuke= this.NapRuke + Integer.parseInt(this.Ruke.getText().toString()) - Integer.parseInt((c.getString(c.getColumnIndex("ObimRuke"))));
            this.NapNoge= this.NapNoge + Integer.parseInt(this.Noge.getText().toString()) - Integer.parseInt((c.getString(c.getColumnIndex("ObimNoge"))));
            this.NapStruk=this.NapStruk + Integer.parseInt(this.Struk.getText().toString()) - Integer.parseInt((c.getString(c.getColumnIndex("ObimStruka"))));

            Profil p = new Profil();
            p.setIme(Ime.getText().toString());
            p.setPrezime(Prezime.getText().toString());

            //db.updateProf(p,this);

            ContentValues cv = new ContentValues();
            cv.put("Ime", Ime.getText().toString());
            cv.put("Prezime", Prezime.getText().toString());
            cv.put("ObimRuke", Integer.parseInt(Ruke.getText().toString()));
            cv.put("ObimStruka", Integer.parseInt(Struk.getText().toString()));
            cv.put("ObimGrudi", Integer.parseInt(Grudi.getText().toString()));
            cv.put("ObimNoge", Integer.parseInt(Noge.getText().toString()));
            cv.put("Tezina",Integer.parseInt(Tezina.getText().toString()));
            cv.put("Visina",Integer.parseInt(Visina.getText().toString()));
            cv.put("NapGrudi",this.NapGrudi);
            cv.put("NapVisina",this.NapVisina);
            cv.put("NapTezina",this.NapTezina);
            cv.put("NapRuke",this.NapRuke);
            cv.put("NapNoge",this.NapNoge);
            cv.put("NapStruka", this.NapStruk);
            int pom = db1.update(TABLE_NAME, cv, "ID = ? ", new String[] {Id});


        }
        catch (SQLException e)
        {
            e.getMessage();
        }

        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



    }

}
