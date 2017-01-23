package com.rgt.nenad.realgymtrainerv2;

import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Nenad on 02-Jul-16.
 */
public class ViewHolderSettings {
    TextView name;
    Switch enable;
    LinearLayout layout;

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public Switch getEnable() {
        return enable;
    }

    public void setEnable(Switch enable) {
        this.enable = enable;
    }

    public LinearLayout getLayout() {
        return layout;
    }

    public void setLayout(LinearLayout layout) {
        this.layout = layout;
    }

    public ViewHolderSettings(TextView name, Switch enable, LinearLayout layout) {

        this.name = name;
        this.enable = enable;
        this.layout = layout;


    }

}
