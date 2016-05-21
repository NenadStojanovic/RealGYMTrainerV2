package com.rgt.nenad.realgymtrainerv2;

/**
 * Created by Nenad on 20-May-16.
 */
public class Profil {
    String ID;
    String Ime;
    String Prezime;
    int ObimRuke;
    int ObimStruka;
    int ObimGrudi;
    int ObimNoge;
    int Visina;
    int Tezina;
    int BMI;

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setIme(String ime) {
        Ime = ime;
    }

    public void setPrezime(String prezime) {
        Prezime = prezime;
    }

    public void setObimRuke(int obimRuke) {
        ObimRuke = obimRuke;
    }

    public void setObimStruka(int obimStruka) {
        ObimStruka = obimStruka;
    }

    public void setObimGrudi(int obimGrudi) {
        ObimGrudi = obimGrudi;
    }

    public void setObimNoge(int obimNoge) {
        ObimNoge = obimNoge;
    }

    public void setVisina(int visina) {
        Visina = visina;
    }

    public void setTezina(int tezina) {
        Tezina = tezina;
    }

    public void setBMI(int BMI) {
        this.BMI = BMI;
    }

    public String getID() {

        return ID;
    }

    public String getIme() {
        return Ime;
    }

    public String getPrezime() {
        return Prezime;
    }

    public int getObimRuke() {
        return ObimRuke;
    }

    public int getObimStruka() {
        return ObimStruka;
    }

    public int getObimGrudi() {
        return ObimGrudi;
    }

    public int getObimNoge() {
        return ObimNoge;
    }

    public int getVisina() {
        return Visina;
    }

    public int getTezina() {
        return Tezina;
    }

    public int getBMI() {
        return BMI;
    }
}
