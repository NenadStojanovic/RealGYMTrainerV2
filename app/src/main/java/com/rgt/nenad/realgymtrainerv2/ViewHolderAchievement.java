package com.rgt.nenad.realgymtrainerv2;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Nenad on 01-Jul-16.
 */
public class ViewHolderAchievement {
    TextView message;
    ImageView image;
    LinearLayout parent;

    public ViewHolderAchievement(TextView message, ImageView image, LinearLayout parent) {
        this.message = message;
        this.image = image;
        this.parent = parent;
    }

    public TextView getMessage() {
        return message;
    }

    public void setMessage(TextView message) {
        this.message = message;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public LinearLayout getParent() {
        return parent;
    }

    public void setParent(LinearLayout parent) {
        this.parent = parent;
    }
}
