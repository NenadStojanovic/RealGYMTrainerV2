package com.rgt.nenad.realgymtrainerv2;

import android.widget.TextView;

import java.util.ArrayList;

public class
        Parent {
    private String mTitle;
    private String tipVezbe;
    private ArrayList<Child> mArrayChildren;

    public String getTipVezbe() {
        return tipVezbe;
    }

    public void setTipVezbe(String tipVezbe) {
        this.tipVezbe = tipVezbe;
    }





    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public ArrayList<Child> getArrayChildren() {
        return mArrayChildren;
    }

    public void setArrayChildren(ArrayList<Child> arrayChildren) {
        mArrayChildren = arrayChildren;
    }
}
