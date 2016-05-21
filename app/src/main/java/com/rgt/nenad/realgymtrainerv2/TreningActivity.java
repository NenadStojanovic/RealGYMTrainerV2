package com.rgt.nenad.realgymtrainerv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


        ArrayList<Parent> arrayParents = new ArrayList<Parent>();
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
                arrayChildren.add(child);

            }
            parent.setArrayChildren(arrayChildren);

            //in this array we add the Parent object. We will use the arrayParents at the setAdapter
            arrayParents.add(parent);
        }

        //sets the adapter that provides data to the list.
        mExpandableList.setAdapter(new MyCustomAdapter(TreningActivity.this, arrayParents));

    }
}

