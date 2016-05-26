package com.festival.tacademy.festivalmate.Data;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class MateTalkRoom implements Serializable {

    public int chatroom_no;
    public String chatroom_img;
    public int chatroom_size;
    public int chatroom_maxSize;
    public List<Artist> matched_artist;
    public int matched_artist_number;
    public  String chatroom_name;
    public int chatroom_host_no;
    public String chatroom_host_name;
    public  int chatroom_festival_no;
    public   String chatroom_festival_name;
    public int chatroom_location;
    public int chatroom_age;

    public String date;

    public List<User> chatroom_mems;
    public String unRead;

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

    public int getChatroom_size() {
        return chatroom_size;
    }

    public void setChatroom_size(int chatroom_size) {
        this.chatroom_size = chatroom_size;
    }

    public int getChatroom_maxSize() {
        return chatroom_maxSize;
    }

    public void setChatroom_maxSize(int chatroom_maxSize) {
        this.chatroom_maxSize = chatroom_maxSize;
    }

    public List<Artist> getMatched_artist() {
        return matched_artist;
    }

    public void setMatched_artist(List<Artist> matched_artist) {
        this.matched_artist = matched_artist;
    }

    public int getMatched_artist_number() {
        return matched_artist_number;
    }

    public void setMatched_artist_number(int matched_artist_number) {
        this.matched_artist_number = matched_artist_number;
    }

    public String getChatroom_name() {
        return chatroom_name;
    }

    public void setChatroom_name(String chatroom_name) {
        this.chatroom_name = chatroom_name;
    }

    public int getChatroom_host_no() {
        return chatroom_host_no;
    }

    public void setChatroom_host_no(int chatroom_host_no) {
        this.chatroom_host_no = chatroom_host_no;
    }

    public String getChatroom_host_name() {
        return chatroom_host_name;
    }

    public void setChatroom_host_name(String chatroom_host_name) {
        this.chatroom_host_name = chatroom_host_name;
    }

    public int getChatroom_festival_no() {
        return chatroom_festival_no;
    }

    public void setChatroom_festival_no(int chatroom_festival_no) {
        this.chatroom_festival_no = chatroom_festival_no;
    }

    public String getChatroom_festival_name() {
        return chatroom_festival_name;
    }

    public void setChatroom_festival_name(String chatroom_festival_name) {
        this.chatroom_festival_name = chatroom_festival_name;
    }

    public int getChatroom_location() {
        return chatroom_location;
    }

    public void setChatroom_location(int chatroom_location) {
        this.chatroom_location = chatroom_location;
    }

    public int getChatroom_age() {
        return chatroom_age;
    }

    public void setChatroom_age(int chatroom_age) {
        this.chatroom_age = chatroom_age;
    }

    public List<User> getChatroom_mems() {
        return chatroom_mems;
    }

    public void setChatroom_mems(List<User> chatroom_mems) {
        this.chatroom_mems = chatroom_mems;
    }

    public String getUnRead() {
        return unRead;
    }

    public void setUnRead(String unRead) {
        this.unRead = unRead;
    }

    public MateTalkRoom(String title, String photo, String name, List<Artist> matching_artists, int prefer_location, int age, List<User> member) {
        this.chatroom_name = title;
        this.chatroom_img = photo;
        this.chatroom_festival_name = name;
        this.matched_artist = matching_artists;
        this.chatroom_location = prefer_location;
        this.chatroom_age = age;
        this.chatroom_mems = member;
    }
    public MateTalkRoom() {

    }




}
