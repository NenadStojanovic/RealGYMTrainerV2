package com.rgt.nenad.realgymtrainerv2;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nenad on 24-Jun-16.
 */
public class ViewHolder{
    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;
    TextView t5;
    LinearLayout l1;
    LinearLayout l2;
    LinearLayout l3;
    LinearLayout l4;
    LinearLayout btnComp;
    LinearLayout child;
   final EditText e1;
   final EditText e2;
   final EditText e3;
   final EditText e4;
   final LinearLayout lin1;
   final LinearLayout lin2;
   final LinearLayout lin3;
   final LinearLayout lin4;

    public ViewHolder(EditText e1, EditText e2, EditText e3, EditText e4, LinearLayout lin1, LinearLayout lin2, LinearLayout lin3, LinearLayout lin4) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
        this.e4 = e4;
        this.lin1 = lin1;
        this.lin2 = lin2;
        this.lin3 = lin3;
        this.lin4 = lin4;
    }


}