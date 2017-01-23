package com.rgt.nenad.realgymtrainerv2;

import android.content.Intent;
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
import java.util.List;


public class TreningActivity extends AppCompatActivity {

    int LineCount1 = 10;
    String Naziv;
    String Opis;
    DBMain db;
    ArrayList<Vezba> vezbe = new ArrayList<Vezba>();

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

        String TipTreninga = GetTipTreninga();
        if(TipTreninga.equals("1"))
            GenerateCircuitTrainning();
        else if(TipTreninga.equals("2"))
            GenerateBeginnersTrainning();
        else
            GenerateAdvancedTrainning();


    }

    public void startTraining(View view){
        Intent intent;
        intent = new Intent(getApplicationContext(), ActiveTrainingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Vezbe", vezbe);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void GenerateAdvancedTrainning() {



        ArrayList<Parent> arrayParents = new ArrayList<Parent>();
        ArrayList<Child> arrayChildren;
        db = new DBMain(this);
        vezbe.clear();
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
            if(brTreninga.equals("5")){
                List<Integer> pomNiz = new ArrayList<Integer>();


                int setBrojac = 0;


                for (int i = 0; i < 6; i++)
                {
                    if(i%2==0 && i!=0)
                        setBrojac++;

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND " + "SET_"+(setBrojac+1)+" = 1 AND active = 1";
                    Cursor c1 = db1.rawQuery( pomStr, new String[]{"Chest"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children

                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setBrURL(LineNumber);
                        child.setSlikaLink("Grudi");
                        parent.setTipVezbe("Grudi");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

            }

            else if(brTreninga.equals("4")){

                List<Integer> pomNiz = new ArrayList<Integer>();


                int setBrojac = 0;
                for (int i = 0; i < 4; i++)
                {

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND " + "SET_"+(setBrojac+1)+" = 1 AND active = 1";
                    setBrojac++;
                    Cursor c1 = db1.rawQuery( pomStr, new String[]{"Back"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children
                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Ledja");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Ledja");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

            }

            else if(brTreninga.equals("3")){
                List<Integer> pomNiz = new ArrayList<Integer>();


                int setBrojac = 0;
                for (int i = 0; i < 6; i++)
                {
                    if(i%2==0 && i!=0)
                        setBrojac++;

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND " + "SET_"+(setBrojac+1)+" = 1 AND active = 1";
                    Cursor c1 = db1.rawQuery( pomStr, new String[]{"Shoulders"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children

                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Ramena");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Ramena");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }



            }

            else if(brTreninga.equals("2")){
                List<Integer> pomNiz = new ArrayList<Integer>();


                int setBrojac = 0;
                for (int i = 0; i < 6; i++)
                {
                    if(i%2==0 && i!=0)
                        setBrojac++;

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND " + "SET_"+(setBrojac+1)+" = 1 AND active = 1";
                    Cursor c1 = db1.rawQuery( pomStr, new String[]{"Legs"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children

                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }
                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Noge");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Noge");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }



            }
            else{


                List<Integer> pomNiz = new ArrayList<Integer>();



                for (int i = 0; i < 4; i++)
                {


                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND " + "SET_"+(i+1)+" = 1 AND active = 1";
                    Cursor c1 = db1.rawQuery( pomStr, new String[]{"Biceps"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children

                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Biceps");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Biceps");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

                List<Integer> pomNiz1 = new ArrayList<Integer>();
                for(int i=0;i<4;i++)
                {

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND " + "SET_"+(i+1)+" = 1 AND active = 1";
                    Cursor c1 = db1.rawQuery(pomStr, new String[]{"Triceps"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children
                    //Naziv
                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Triceps");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Triceps");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

            }

            db1.close();
            db.close();

        }
        catch (SQLException e){
            e.getMessage();
        }
        mExpandableList.setAdapter(new MyCustomAdapter(TreningActivity.this, arrayParents));
    }

    private void GenerateCircuitTrainning() {
        ArrayList<Parent> arrayParents = new ArrayList<Parent>();
        ArrayList<Child> arrayChildren;
        vezbe.clear();
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




                List<Integer> pomNiz = new ArrayList<Integer>();



                for (int i = 0; i < 2; i++)
                {


                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND active = 1";
                    Cursor c1 = db1.rawQuery( pomStr, new String[]{"Chest"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children

                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Grudi");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Grudi");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

                List<Integer> pomNiz1 = new ArrayList<Integer>();
                for(int i=0; i<2 ;i++)
                {

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND active = 1";
                    Cursor c1 = db1.rawQuery(pomStr, new String[]{"Biceps"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children
                    //Naziv
                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));
                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Biceps");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Biceps");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }



                pomNiz = new ArrayList<Integer>();



                for (int i = 0; i < 2; i++)
                {

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND active = 1";
                    Cursor c1 = db1.rawQuery(pomStr, new String[]{"Back"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children

                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }
                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Ledja");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Ledja");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

                 pomNiz1 = new ArrayList<Integer>();
                for(int i=0;i<2;i++)
                {

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND active = 1";
                    Cursor c1 = db1.rawQuery(pomStr, new String[]{"Triceps"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children
                    //Naziv
                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Triceps");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Triceps");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }



                 pomNiz = new ArrayList<Integer>();



                for (int i = 0; i < 2; i++)
                {

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND active = 1";

                    Cursor c1 = db1.rawQuery(pomStr, new String[]{"Shoulders"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children

                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Ramena");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Ramena");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

                 pomNiz1 = new ArrayList<Integer>();
                for(int i=0;i<2;i++)
                {

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND active = 1";
                    Cursor c1 = db1.rawQuery(pomStr, new String[]{"Legs"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children
                    //Naziv
                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Noge");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Noge");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

            db1.close();
            db.close();



        }
        catch (SQLException e){
            e.getMessage();
        }
        mExpandableList.setAdapter(new MyCustomAdapter(TreningActivity.this, arrayParents));
    }

    public String GetTipTreninga(){
        String brTreninga = new String();
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
            Cursor c = db1.rawQuery("SELECT TipTreninga FROM " + "Profil" + " WHERE id = ?", new String[]{"1"});


            c.moveToFirst();
             brTreninga = c.getString(c.getColumnIndex("TipTreninga"));

        }
        catch (SQLException e){
            e.getMessage();
        }
        return brTreninga;
    }

    public void GenerateBeginnersTrainning() {
        ArrayList<Parent> arrayParents = new ArrayList<Parent>();
        ArrayList<Child> arrayChildren;
        vezbe.clear();
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

                List<Integer> pomNiz = new ArrayList<Integer>();



                for (int i = 0; i < 4; i++)
                    {

                      // pomNiz.clear();         //ovo ces da izbacis kad sredis bazu da imas jedinstvene vezbe za odredjene setove
                        String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND " + "SET_"+(i+1)+" = 1 AND active = 1";
                        Cursor c1 = db1.rawQuery( pomStr, new String[]{"Chest"});
                        c1.moveToFirst();
                        int RandInd1;
                        int LineNumber;
                        //for each "i" create a new Parent object to set the title and the children

                        Parent parent = new Parent();
                        do {


                            c1.moveToPosition(0);
                            double val2 = Math.random() * c1.getCount();
                            RandInd1 = (int) Math.ceil(val2 - 1);
                            if(RandInd1 == 0)
                                RandInd1 = 1;



                            for (int j = 0; j < RandInd1; j++) {
                                if(c1.getCount()>1)
                                    c1.moveToNext();
                            }
                        }
                        while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                        pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                        parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));


                        LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                        //opis vezbi
                        arrayChildren = new ArrayList<Child>();
                        Child child = new Child();
                        for (int l = 0; l < 1; ++l) {

                            if(LineNumber==-1)
                                Opis = "NOTE: This exercise is added by user!";
                            else {

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
                            }

                            child.setTitle(Opis);
                            child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                            child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                            child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                            child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                            child.setSlikaLink("Grudi");
                            child.setBrURL(LineNumber);
                            parent.setTipVezbe("Grudi");

                            Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                            vezbe.add(v);
                            arrayChildren.add(child);

                        }
                        parent.setArrayChildren(arrayChildren);

                        //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                        arrayParents.add(parent);
                    }

                List<Integer> pomNiz1 = new ArrayList<Integer>();
                for(int i=0;i<4;i++)
                {

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND " + "SET_"+(i+1)+" = 1 AND active = 1";
                    Cursor c1 = db1.rawQuery(pomStr, new String[]{"Biceps"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children
                    //Naziv
                    Parent parent = new Parent();
                    do {

                        c1.moveToPosition(0);
                        int val = c1.getCount();
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));

                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Biceps");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Biceps");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

            }
            else if(brTreninga.equals("2")) {
                List<Integer> pomNiz = new ArrayList<Integer>();



                for (int i = 0; i < 4; i++)
                {

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND " + "SET_"+(i+1)+" = 1 AND active = 1";
                    Cursor c1 = db1.rawQuery(pomStr, new String[]{"Back"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children
                    Parent parent = new Parent();
                    do {

                        c1.moveToPosition(0);
                        int val = c1.getCount();
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));


                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Ledja");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Ledja");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

                List<Integer> pomNiz1 = new ArrayList<Integer>();
                for(int i=0;i<4;i++)
                {

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND " + "SET_"+(i+1)+" = 1 AND active = 1";
                    Cursor c1 = db1.rawQuery(pomStr, new String[]{"Triceps"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children
                    //Naziv
                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));


                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Triceps");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Triceps");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

            }
            else  {
                List<Integer> pomNiz = new ArrayList<Integer>();



                for (int i = 0; i < 4; i++)
                {

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND " + "SET_"+(i+1)+" = 1 AND active = 1";

                    Cursor c1 = db1.rawQuery(pomStr, new String[]{"Shoulders"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children
                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));


                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Ramena");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Ramena");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

                List<Integer> pomNiz1 = new ArrayList<Integer>();
                for(int i=0;i<4;i++)
                {

                    String pomStr ="SELECT * FROM " + "Trening" + " WHERE TIP_VEZBE = ? AND " + "SET_"+(i+1)+" = 1 AND active = 1";
                    Cursor c1 = db1.rawQuery(pomStr, new String[]{"Legs"});
                    c1.moveToFirst();
                    int RandInd1;
                    int LineNumber;
                    //for each "i" create a new Parent object to set the title and the children
                    //Naziv
                    Parent parent = new Parent();
                    do {


                        c1.moveToPosition(0);
                        double val2 = Math.random() * c1.getCount();
                        RandInd1 = (int) Math.ceil(val2 - 1);
                        if(RandInd1 == 0)
                            RandInd1 = 1;



                        for (int j = 0; j < RandInd1; j++) {
                            if(c1.getCount()>1)
                                c1.moveToNext();
                        }
                    }
                    while(pomNiz.contains(Integer.parseInt(c1.getString(c1.getColumnIndex("ID")))));
                    pomNiz.add(Integer.parseInt(c1.getString(c1.getColumnIndex("ID"))));


                    parent.setTitle(c1.getString(c1.getColumnIndex("NAZIV")));

                    LineNumber = Integer.parseInt(c1.getString(c1.getColumnIndex("BR_OPIS")));


                    //opis vezbi
                    arrayChildren = new ArrayList<Child>();
                    Child child = new Child();
                    for (int l = 0; l < 1; ++l) {
                        if(LineNumber==-1)
                            Opis = "NOTE: This exercise is added by user!";
                        else {

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
                        }

                        child.setTitle(Opis);
                        child.setFirstSet(c1.getString(c1.getColumnIndex("TEZINA_1")));
                        child.setSecondSet(c1.getString(c1.getColumnIndex("TEZINA_2")));
                        child.setThirdSet(c1.getString(c1.getColumnIndex("TEZINA_3")));
                        child.setFourthSet(c1.getString(c1.getColumnIndex("TEZINA_4")));
                        child.setSlikaLink("Noge");
                        child.setBrURL(LineNumber);
                        parent.setTipVezbe("Noge");

                        Vezba v = new Vezba(parent.getTitle(), child.getFirstSet(), child.getSecondSet(), child.getThirdSet(), child.getFourthSet(), c1.getString(c1.getColumnIndex("ID")));
                        vezbe.add(v);
                        arrayChildren.add(child);

                    }
                    parent.setArrayChildren(arrayChildren);

                    //in this array we add the Parent object. We will use the arrayParents at the setAdapter
                    arrayParents.add(parent);
                }

            }

            db1.close();
            db.close();

        }
        catch (SQLException e){
            e.getMessage();
        }
        mExpandableList.setAdapter(new MyCustomAdapter(TreningActivity.this, arrayParents));
    }



}

