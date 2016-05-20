package com.festival.tacademy.festivalmate.Data;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-05-17.
 */
public class Artist implements Serializable {
    public String getName() {
        return name;
    }

    public Artist(String name) {
        this.name = name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public Artist(String name, int photo) {

        this.name = name;
        this.photo = photo;
    }
    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean check;
    public String name;
    public int photo;

}
