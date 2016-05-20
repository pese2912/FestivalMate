package com.festival.tacademy.festivalmate.Data;

import android.graphics.drawable.Drawable;
import android.widget.CheckBox;

/**
 * Created by Tacademy on 2016-05-13.
 */
public class PreferenceArtist {

    private String name;
    private Drawable image;
    private boolean check;

    public PreferenceArtist(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
