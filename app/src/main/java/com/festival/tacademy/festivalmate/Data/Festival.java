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

    public String getFestival_location_url() {
        return festival_location_url;
    }

    public void setFestival_location_url(String festival_location_url) {
        this.festival_location_url = festival_location_url;
    }

    public String getFestival_location_img() {
        return festival_location_img;
    }

    public void setFestival_location_img(String festival_location_img) {
        this.festival_location_img = festival_location_img;
    }

    public String festival_location_img;
    public String festival_location_url;

    public int mem_going_check;
    public List<Lineup> festival_lineups;
    public List<User>  festival_going_mem = new ArrayList<>();

    public List<Festival> getFestival_list() {
        return festival_list;
    }

    public void setFestival_list(List<Festival> festival_list) {
        this.festival_list = festival_list;
    }

    public List<Festival> festival_list;


    int photo_location;
    String date;

    public String festival_ticket_link;
    public String festival_homepage;

    public String getFestival_ticket_link() {
        return festival_ticket_link;
    }

    public void setFestival_ticket_link(String festival_ticket_link) {
        this.festival_ticket_link = festival_ticket_link;
    }

    public String getFestival_homepage() {
        return festival_homepage;
    }

    public void setFestival_homepage(String festival_homepage) {
        this.festival_homepage = festival_homepage;
    }

    public Festival() {
    }


    public Festival(String name, String photo, String date, String location, List<User> letsgo_users, List<Lineup> lineup) {
        this.festival_name = name;
        this.festival_img = photo;
        this.date = date;
        this.festival_location = location;
        this. festival_going_mem = letsgo_users;
        this.festival_lineups = lineup;
    }

    public Festival(String festival_name) {
        this.festival_name = festival_name;
    }

    public int getFestival_no() {
        return festival_no;
    }

    public void setFestival_no(int festival_no) {
        this.festival_no = festival_no;
    }

    public String getFestival_name() {
        return festival_name;
    }

    public void setFestival_name(String festival_name) {
        this.festival_name = festival_name;
    }

    public String getFestival_img() {
        return festival_img;
    }

    public void setFestival_img(String festival_img) {
        this.festival_img = festival_img;
    }

    public String getFestival_location() {
        return festival_location;
    }

    public void setFestival_location(String festival_location) {
        this.festival_location = festival_location;
    }

    public int getMem_going_check() {
        return mem_going_check;
    }

    public void setMem_going_check(int mem_going_check) {
        this.mem_going_check = mem_going_check;
    }

    public List<Lineup> getFestival_lineups() {
        return festival_lineups;
    }

    public void setFestival_lineups(List<Lineup> festival_lineups) {
        this.festival_lineups = festival_lineups;
    }

    public List<User> getFestival_going_mem() {
        return festival_going_mem;
    }

    public void setFestival_going_mem(List<User> festival_going_mem) {
        this.festival_going_mem = festival_going_mem;
    }

    public List<Lineup> getLineups() {
        return festival_lineups;
    }

    public void setLineups(List<Lineup> lineups) {
        this.festival_lineups = lineups;
    }

    public int getPhoto_location() {
        return photo_location;
    }

    public void setPhoto_location(int photo_location) {
        this.photo_location = photo_location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
