package com.rgt.nenad.realgymtrainerv2;

import java.io.Serializable;

/**
 * Created by Nenad on 23-Jun-16.
 */
public class Vezba implements Serializable {
    private String naziv;
    private String firstSet;
    private String secondSet;
    private String thirdSet;
    private String fourthSet;
    private String ID;





    public Vezba(String naziv, String firstSet, String secondSet, String thirdSet, String fourthSet,String ID) {
        this.naziv = naziv;
        this.firstSet = firstSet;
        this.secondSet = secondSet;
        this.thirdSet = thirdSet;
        this.fourthSet = fourthSet;
        this.ID = ID;
    }

    public String getNaziv() {
        return naziv;
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

    public String getID() {
        return ID;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
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

    public void setID(String ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
