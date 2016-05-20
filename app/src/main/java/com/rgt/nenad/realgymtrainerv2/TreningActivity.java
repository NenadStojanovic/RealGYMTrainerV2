package com.rgt.nenad.realgymtrainerv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import java.util.ArrayList;

public class TreningActivity extends AppCompatActivity {

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
        ArrayList<String> arrayChildren = new ArrayList<String>();

        //here we set the parents and the children
        for (int i = 0; i < 5; i++) {
            //for each "i" create a new Parent object to set the title and the children
            Parent parent = new Parent();
            parent.setTitle("Vezba " + i);

            arrayChildren = new ArrayList<String>();
            for (int j = 0; j < 1; j++) {
                arrayChildren.add("Ovo je text za opis vezbe ");
            }
            parent.setArrayChildren(arrayChildren);

            //in this array we add the Parent object. We will use the arrayParents at the setAdapter
            arrayParents.add(parent);
        }

        //sets the adapter that provides data to the list.
        mExpandableList.setAdapter(new MyCustomAdapter(TreningActivity.this, arrayParents));

    }
}

