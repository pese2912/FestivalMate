package com.festival.tacademy.festivalmate.Data;

import android.graphics.drawable.Drawable;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class MateTalkWaitList {
    public String name;
    public String title;
    public String people;
    public String date;
    public String waitPeople;

    public Drawable photo;

    public Drawable getPhoto() {
        return photo;
    }

    public void setPhoto(Drawable photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWaitPeople() {
        return waitPeople;
    }

    public void setWaitPeople(String waitPeople) {
        this.waitPeople = waitPeople;
    }

}
