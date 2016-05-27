package com.festival.tacademy.festivalmate.Data;

/**
 * Created by J.K.Lee on 2016-05-25.
 */
public class FestivalDetailResult {

    public int success;
    public String message;
    public Festival result;

    public Festival getResult() {
        return result;
    }

    public void setResult(Festival result) {
        this.result = result;
    }
}
