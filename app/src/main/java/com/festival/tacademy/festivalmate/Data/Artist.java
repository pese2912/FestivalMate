package com.festival.tacademy.festivalmate.Data;

import java.io.Serializable;

/**
 * Created by Tacademy on 2016-05-17.
 */
public class Artist implements Serializable {
    public String getName() {
        return artist_name;
    }

    public Artist(String name) {
        this.artist_name = name;
    }
    public void setName(String name) {
        this.artist_name = name;
    }

    public String getPhoto() {
        return artist_img;
    }

    public void setPhoto(String photo) {
        this.artist_img = photo;
    }

    public Artist(String name, String photo) {

        this.artist_name = name;
        this.artist_img = photo;
    }

    public int isCheck() {
        return artist_fav_state;
    }

    public void setCheck(int check) {
        this.artist_fav_state = check;
    }


    public int getArtist_img_no() {
        return artist_img_no;
    }

    public void setArtist_img_no(int artist_img_no) {
        this.artist_img_no = artist_img_no;
    }

    public int getArtist_no() {
        return artist_no;
    }

    public void setArtist_no(int artist_no) {
        this.artist_no = artist_no;
    }

    public int artist_no;
    public String artist_name;
    public int artist_img_no;
    public String artist_img;
    public int artist_fav_state=0;

}
