package com.rgt.nenad.realgymtrainerv2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
        try {
            SQLiteDatabase db1;
            db1 = openOrCreateDatabase("rgtbaza", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            ContentValues cv = new ContentValues();
            cv.put("Ime", Ime.getText().toString());
            cv.put("Prezime", Prezime.getText().toString());
            cv.put("ObimRuke", Ruke.getText().toString());
            cv.put("ObimStruka", Struk.getText().toString());
            cv.put("ObimGrudi", Grudi.getText().toString());
            cv.put("ObimNoge", Noge.getText().toString());
            cv.put("Tezina", Tezina.getText().toString());
            cv.put("Visina", Visina.getText().toString());
            int pom = db1.update(TABLE_NAME, cv, "ID = ? ", new String[] {Id});
            db1.close();
        }
        catch (Exception e)
        {
            e.getMessage();
        }

        Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);



    }

}
