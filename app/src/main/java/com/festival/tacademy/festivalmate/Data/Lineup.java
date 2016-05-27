package com.festival.tacademy.festivalmate.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-17.
 */
public class Lineup implements Serializable{

    String festival_date;
    List<Artist> festival_lineup = new ArrayList<>();

    public String getDate()
    {
        return festival_date;
    }

    public void setDate(String date) {
        this.festival_date = date;
    }

    public List<Artist> getLineup() {
        return festival_lineup;
    }

    public void setLineup(List<Artist> lineup) {
        this.festival_lineup = lineup;
    }

    public Lineup(String date, List<Artist> lineup) {

        this.festival_date = date;
        this.festival_lineup = lineup;
    }
    public Lineup(String date) {

        this.festival_date = date;
    }
}
