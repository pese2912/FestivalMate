package com.festival.tacademy.festivalmate.Data;

import android.graphics.drawable.Drawable;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class chatroom_waiting {
    public int getChatroom_waiting_no() {
        return chatroom_waiting_no;
    }

    public void setChatroom_waiting_no(int chatroom_waiting_no) {
        this.chatroom_waiting_no = chatroom_waiting_no;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getMem_img() {
        return mem_img;
    }

    public void setMem_img(String mem_img) {
        this.mem_img = mem_img;
    }

    public String getMem_state_msg() {
        return mem_state_msg;
    }

    public void setMem_state_msg(String mem_state_msg) {
        this.mem_state_msg = mem_state_msg;
    }

    public int chatroom_waiting_no;

    public int getMem_no() {
        return mem_no;
    }

    public void setMem_no(int mem_no) {
        this.mem_no = mem_no;
    }

    public int mem_no;
    public String mem_name;
    public String mem_img;
    public String mem_state_msg;

}
