package com.festival.tacademy.festivalmate.Data;

import java.io.Serializable;

/**
 * Created by J.K.Lee on 2016-05-16.
 */
public class User implements Serializable {
    String id;
    int photo;

    public String getId() {
        return id;
    }

    public User(String id, int photo) {
        this.id = id;
        this.photo = photo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
