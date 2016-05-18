package com.festival.tacademy.festivalmate.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-13.
 */
public class Festival implements Serializable {

    String name;
    int photo;
    String date;
    String location;
    List<User> letsgo_users = new ArrayList<>();
    List<Lineup> lineups = new ArrayList<>();


    public List<Lineup> getLineups() {
        return lineups;
    }

    public void setLineups(List<Lineup> lineups) {
        this.lineups = lineups;
    }

    public Festival() {
    }

    public Festival(String name, int photo, String date, String location, List<User> letsgo_users, List<Lineup> lineup) {
        this.name = name;
        this.photo = photo;
        this.date = date;
        this.location = location;
        this.letsgo_users = letsgo_users;
        this.lineups = lineup;
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

    public List<User> getLetsgo_users() {
        return letsgo_users;
    }

    public void setLetsgo_users(List<User> letsgo_users) {
        this.letsgo_users = letsgo_users;
    }
}
