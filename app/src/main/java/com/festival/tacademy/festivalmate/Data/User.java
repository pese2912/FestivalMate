package com.festival.tacademy.festivalmate.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-16.
 */
public class User implements Serializable {

    String id;
    int photo;
    String name;
    String email;
    List<Festival> letsgo = new ArrayList<>();
    List<Artist> artist = new ArrayList<>();
    int bad_cnt;

    public User(String id, int photo, String name, String email, List<Festival> letsgo, List<Artist> artist) {
        this.id = id;
        this.photo = photo;
        this.name = name;
        this.email = email;
        this.letsgo = letsgo;
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public User(String id, int photo) {
        this.id = id;
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
        this.id = id;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo)
    {
        this.photo = photo;
    }
}
