package com.rgt.nenad.realgymtrainerv2;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;







public class TreningActivity extends AppCompatActivity {

    int LineCount1 = 10;
    String Naziv;
    String Opis;
    DBMain db;

    //override BackButtonTransition
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private ExpandableListView mExpandableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trening);

        //Hiding ActionBar
        getSupportActionBar().hide();


        mExpandableList = (ExpandableListView) findViewById(R.id.expandable_list);

        GenerateBeginnersTrainning();
       /* ArrayList<Parent> arrayParents = new ArrayList<Parent>();
        ArrayList<Child> arrayChildren;

        //OVO CITA TXT DATOTEKE ZA NAZIV I OPIS
        //PARENT

        //TextView papa = (TextView) findViewById(R.id.list_item_text_view);
        //TextView dete = (TextView) findViewById(R.id.list_item_text_child);

        int duz=LineCount1;
        int RandInd1;
        int ind;

        for (int i = 0; i < 3; i++) {
            //for each "i" create a new Parent object to set the title and the children
            //Naziv
            RandInd1=(int)Math.ceil(Math.random()*(LineCount1-1));
            Parent parent = new Parent();

            parent.setTitle((i+1)+"-"+"Naziv");



            //opis vezbi
            arrayChildren = new ArrayList<Child>();
            Child child=new Child();
            for (int l = 0; l < 1; ++l) {
                try {
                    int rand=RandInd1;
                    BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("grudiopisvezbi.txt")));
                    for(int j = 0; j < rand; ++j) {
                        br.readLine();


                    }

                    Opis = br.readLine();


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                child.setTitle(Opis);
                arrayChildren.add(child);

            }
            parent.setArrayChildren(arrayChildren);

            //in this array we add the Parent object. We will use the arrayParents at the setAdapter
            arrayParents.add(parent);
        }

        //sets the adapter that provides data to the list.
        mExpandableList.setAdapter(new MyCustomAdapter(TreningActivity.this, arrayParents));*/

    }

    public void GenerateBeginnersTrainning() {
        ArrayList<Parent> arrayParents = new ArrayList<Parent>();
        ArrayList<Child> arrayChildren;
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
            Cursor c = db1.rawQuery("SELECT * FROM " + "Profil" + " WHERE id = ?", new String[]{"1"});


            c.moveToFirst();
            String brTreninga = c.getString(c.getColumnIndex("BrojTreninga"));
            if(brTreninga.equals("3"))
            {



                for (int i = 0; i < 4; i++)
                    {
                        Cursor c1 = db1.rawQuery("SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ?", new String[]{"Chest"});
                        c.moveToFirst();
                        int RandInd1;
                        int LineNumber;
                        //for each "i" create a new Parent object to set the title and the children
                        //Naziv
                        int val = c1.getCount();
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;
                        Parent parent = new Parent();
                        for (int j = 0; j < RandInd1; j++) {
                            c1.moveToNext();
                        }

                        parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                        LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                        //opis vezbi
                        arrayChildren = new ArrayList<Child>();
                        Child child = new Child();
                        for (int l = 0; l < 1; ++l) {
                            try {

                                BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("grudiopisvezbi.txt")));
                                for (int j = 0; j < LineNumber; ++j) {
                                    br.readLine();

                       /* if(Opis==null)
                        {
                            rand=RandInd1;
                            j=0;
                            br = new BufferedReader(new InputStreamReader(getAssets().open("grudiOpisVezbi.txt")));
                        }*/
                                }

                                Opis = br.readLine();


                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            child.setTitle(Opis);
                            child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                            child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                            child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                            child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                            child.setSlikaLink("Grudi");
                            arrayChildren.add(child);

                        }
                        parent.setArrayChildren(arrayChildren);

                        //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                        arrayParents.add(parent);
                    }

                for(int i=0;i<4;i++)
                {
                    Cursor c1 = db1.rawQuery("SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? ", new String[]{"Biceps"});
                    c.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children
                    //Naziv
                    int val = c1.getCount();
                    double val2 = Math.random() * c1.getCount();
                    RandInd1 = (int) Math.ceil(val2 - 1);
                    if(RandInd1 == 0)
                        RandInd1 = 1;
                    Parent parent = new Parent();
                    for (int j = 0; j < RandInd1; j++) {
                        c1.moveToNext();
                    }

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        try {

                            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("grudiopisvezbi.txt")));
                            for (int j = 0; j < LineNumber; ++j) {
                                br.readLine();

                       /* if(Opis==null)
                        {
                            rand=RandInd1;
                            j=0;
                            br = new BufferedReader(new InputStreamReader(getAssets().open("grudiOpisVezbi.txt")));
                        }*/
                            }

                            Opis = br.readLine();


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Ruke");
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

            }



        }
        catch (SQLException e){
            e.getMessage();
        }
        mExpandableList.setAdapter(new MyCustomAdapter(TreningActivity.this, arrayParents));
    }

}

