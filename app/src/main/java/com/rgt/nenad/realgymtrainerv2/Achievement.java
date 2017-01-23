package com.rgt.nenad.realgymtrainerv2;

/**
 * Created by Nenad on 01-Jul-16.
 */
public class Achievement {
    String type;
    String name;
    String active;
    String value;
    String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Achievement(String type, String name, String active, String value, String id) {
        this.type = type;
        this.name = name;
        this.active = active;
        this.value = value;
        this.id = id;
    }
}
