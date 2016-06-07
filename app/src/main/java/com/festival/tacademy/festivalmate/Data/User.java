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
    public String mem_img;
    public  String mem_name;
    public String mem_state_msg;


    public int mem_location;
    public  List<Festival> mem_going = new ArrayList<>();
    public List<Artist> mem_fav_artist = new ArrayList<>();
    public int bad_cnt;


    public int chatroom_mems_no;
    public String chatroom_mem_name;
    public String chatroom_mem_img;

    public int getMem_location() {
        return mem_location;
    }

    public void setMem_location(int mem_location) {
        this.mem_location = mem_location;
    }

    public int getChatroom_mems_no() {
        return chatroom_mems_no;
    }

    public void setChatroom_mems_no(int chatroom_mems_no) {
        this.chatroom_mems_no = chatroom_mems_no;
    }

    public String getChatroom_mem_name() {
        return chatroom_mem_name;
    }

    public void setChatroom_mem_name(String chatroom_mem_name) {
        this.chatroom_mem_name = chatroom_mem_name;
    }

    public String getChatroom_mem_img() {
        return chatroom_mem_img;
    }

    public void setChatroom_mem_img(String chatroom_mem_img) {
        this.chatroom_mem_img = chatroom_mem_img;
    }

    public int getMem_no() {
        return mem_no;
    }

    public String getMem_state_msg() {
        return mem_state_msg;
    }

    public void setMem_state_msg(String mem_state_msg) {
        this.mem_state_msg = mem_state_msg;
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
    public User( String mem_id,String photo, String name,  List<Festival> letsgo, List<Artist> artist) {
        this.mem_img = photo;
        this.mem_name = name;
        this.mem_id = mem_id;
        this.mem_going = letsgo;
        this.mem_fav_artist = artist;
    }

    public User(){

    }

    public String getId() {
        return mem_id;
    }

    public User(String id, String photo) {
        this.mem_id = id;
        this.mem_img = photo;
    }

    public String getName() {
        return mem_name;
    }

    public void setName(String name) {
        this.mem_name = name;
    }


    public List<Festival> getMem_going() {
        return mem_going;
    }

    public void setMem_going(List<Festival> mem_going) {
        this.mem_going = mem_going;
    }

    public List<Artist> getArtist() {
        return mem_fav_artist;
    }

    public void setArtist(List<Artist> artist) {
        this.mem_fav_artist = artist;
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

    public String getPhoto() {
        return mem_img;
    }

    public void setPhoto(String photo)
    {
        this.mem_img = photo;
    }
}
