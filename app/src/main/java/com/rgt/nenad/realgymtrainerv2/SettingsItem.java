package com.rgt.nenad.realgymtrainerv2;

/**
 * Created by Nenad on 02-Jul-16.
 */
public class SettingsItem {
    String name;
    String ID;
    String active;

    public SettingsItem(String name, String ID, String active) {
        this.name = name;
        this.ID = ID;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}
