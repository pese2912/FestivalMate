package com.festival.tacademy.festivalmate.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-17.
 */

public class FestibalLineUp {

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Artist> getArtist() {
        return artist;
    }

    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }

    public String data;
    public List<Artist> artist = new ArrayList<>();

    public String getFestival_date() {
        return festival_date;
    }

    public void setFestival_date(String festival_date) {
        this.festival_date = festival_date;
    }

    public String festival_date;

}
