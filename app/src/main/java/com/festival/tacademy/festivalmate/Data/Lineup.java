package com.festival.tacademy.festivalmate.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-17.
 */
public class Lineup implements Serializable{
    String date;
    List<Artist> lineup = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Artist> getLineup() {
        return lineup;
    }

    public void setLineup(List<Artist> lineup) {
        this.lineup = lineup;
    }

    public Lineup(String date, List<Artist> lineup) {

        this.date = date;
        this.lineup = lineup;
    }
}
