package com.rgt.nenad.realgymtrainerv2;

import android.widget.TextView;

import java.util.ArrayList;
import android.widget.TextView;


public class Child {
    private String mTitle1;
    private String firstSet;
    private String secondSet;
    private String thirdSet;
    private String fourthSet;
    private String slikaLink;

    public void setSlikaLink(String slikaLink) {
        this.slikaLink = slikaLink;
    }

    public String getSlikaLink() {

        return slikaLink;
    }

    public void setFirstSet(String firstSet) {
        this.firstSet = firstSet;
    }

    public void setSecondSet(String secondSet) {
        this.secondSet = secondSet;
    }

    public void setThirdSet(String thirdSet) {
        this.thirdSet = thirdSet;
    }

    public void setFourthSet(String fourthSet) {
        this.fourthSet = fourthSet;
    }

    public String getFirstSet() {

        return firstSet;
    }

    public String getSecondSet() {
        return secondSet;
    }

    public String getThirdSet() {
        return thirdSet;
    }

    public String getFourthSet() {
        return fourthSet;
    }

    public String getTitle() {
        return mTitle1;
    }

    public void setTitle(String title) {
        mTitle1 = title;

    }
}
