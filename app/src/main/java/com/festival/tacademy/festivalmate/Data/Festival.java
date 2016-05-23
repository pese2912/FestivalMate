package com.festival.tacademy.festivalmate.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-13.
 */
public class Festival implements Serializable {


    public int festival_no;
    public String festival_name;
    public String festival_img;
    public String festival_location;
    public int mem_going_check;


    public List<FestibalLineUp> getFestival_lineups() {
        return festival_lineups;
    }

    public void setFestival_lineups(List<FestibalLineUp> festival_lineups) {
        this.festival_lineups = festival_lineups;
    }

    public List<FestibalLineUp> festival_lineups;

   public List<User> letsgo_users = new ArrayList<>();
    public List<Lineup> lineups = new ArrayList<>();

    int photo_location;
    String date;


    public int getFestival_no() {
        return festival_no;
    }

    public void setFestival_no(int festival_no) {
        this.festival_no = festival_no;
    }

    public int getMem_going_check() {
        return mem_going_check;
    }

    public void setMem_going_check(int mem_going_check) {
        this.mem_going_check = mem_going_check;
    }


    public int getPhoto_location() {
        return photo_location;
    }

    public void setPhoto_location(int photo_location) {
        this.photo_location = photo_location;
    }

    public Festival(String name) {
        this.festival_name = name;
    }

    public List<Lineup> getLineups() {
        return lineups;
    }

    public void setLineups(List<Lineup> lineups) {
        this.lineups = lineups;
    }

    public Festival() {
    }

    public Festival(String name, String photo, String date, String location, List<User> letsgo_users, List<Lineup> lineup) {
        this.festival_name = name;
        this.festival_img = photo;
        this.date = date;
        this.festival_location = location;
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
        return festival_location;
    }

    public void setLocation(String location) {
        this.festival_location = location;
    }

    public String getName() {
        return festival_name;
    }

    public void setName(String name) {
        this.festival_name = name;
    }

    public String getPhoto() {
        return festival_img;
    }

    public void setPhoto(String photo) {
        this.festival_img = photo;
    }

    public List<User> getLetsgo_users() {
        return letsgo_users;
    }

    public void setLetsgo_users(List<User> letsgo_users) {
        this.letsgo_users = letsgo_users;
    }
}
