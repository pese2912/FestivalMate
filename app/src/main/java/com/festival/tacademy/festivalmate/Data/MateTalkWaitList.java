package com.festival.tacademy.festivalmate.Data;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class MateTalkWaitList {

    public int chatroom_no;
    public String chatroom_img;
    public String chatroom_festival;
    public String chatroom_name;
    public List<Lineup> festival_lineups;
    public int chatroom_wait_num;

    public int getChatroom_no() {
        return chatroom_no;
    }

    public void setChatroom_no(int chatroom_no) {
        this.chatroom_no = chatroom_no;
    }

    public String getChatroom_img() {
        return chatroom_img;
    }

    public void setChatroom_img(String chatroom_img) {
        this.chatroom_img = chatroom_img;
    }

    public String getChatroom_festival() {
        return chatroom_festival;
    }

    public void setChatroom_festival(String chatroom_festival) {
        this.chatroom_festival = chatroom_festival;
    }

    public String getChatroom_name() {
        return chatroom_name;
    }

    public void setChatroom_name(String chatroom_name) {
        this.chatroom_name = chatroom_name;
    }

    public List<Lineup> getFestival_lineups() {
        return festival_lineups;
    }

    public void setFestival_lineups(List<Lineup> festival_lineups) {
        this.festival_lineups = festival_lineups;
    }

    public int getChatroom_wait_num() {
        return chatroom_wait_num;
    }

    public void setChatroom_wait_num(int chatroom_wait_num) {
        this.chatroom_wait_num = chatroom_wait_num;
    }
}
