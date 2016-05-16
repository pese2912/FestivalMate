package com.festival.tacademy.festivalmate.Data;

/**
 * Created by J.K.Lee on 2016-05-13.
 */
public class Festival {
    String name;
    int photo;
    String date;
    String location;

    public Festival() {
    }

    public Festival(String name, int photo, String date, String location) {
        this.name = name;
        this.photo = photo;
        this.date = date;
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
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
}
