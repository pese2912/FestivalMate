package com.festival.tacademy.festivalmate.Data;

import android.graphics.drawable.Drawable;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class MateTalkRoom {

    private  String name;
    private  String content;
    private Drawable photo;
    private String number;
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Drawable getPhoto() {
        return photo;
    }

    public void setPhoto(Drawable photo) {
        this.photo = photo;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUnRead() {
        return unRead;
    }

    public void setUnRead(String unRead) {
        this.unRead = unRead;
    }

    private String unRead;

}
