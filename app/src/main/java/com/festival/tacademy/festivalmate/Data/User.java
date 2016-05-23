package com.festival.tacademy.festivalmate.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-16.
 */
public class User implements Serializable {



    public int mem_no;
    public String mem_id;
    public int photo;
    public  String name;
    public String email;
    public  List<Festival> letsgo = new ArrayList<>();
    public List<Artist> artist = new ArrayList<>();
    public int bad_cnt;



    public int getMem_no() {
        return mem_no;
    }

    public void setMem_no(int mem_no) {
        this.mem_no = mem_no;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }
    public User(String id, int photo, String name, String email, List<Festival> letsgo, List<Artist> artist) {
        this.mem_id = id;
        this.photo = photo;
        this.name = name;
        this.email = email;
        this.letsgo = letsgo;
        this.artist = artist;
    }

    public String getId() {
        return mem_id;
    }

    public User(String id, int photo) {
        this.mem_id = id;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Festival> getLetsgo() {
        return letsgo;
    }

    public void setLetsgo(List<Festival> letsgo) {
        this.letsgo = letsgo;
    }

    public List<Artist> getArtist() {
        return artist;
    }

    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }

    public int getBad_cnt() {
        return bad_cnt;
    }

    public void setBad_cnt(int bad_cnt) {
        this.bad_cnt = bad_cnt;
    }

    public void setId(String id) {
        this.mem_id = id;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo)
    {
        this.photo = photo;
    }
}
