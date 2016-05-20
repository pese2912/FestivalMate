package com.festival.tacademy.festivalmate.Data;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class MateTalkRoom {

    private  String festival_name;
    private  String content;
    private String number;
    private String date;

    String title;
    int photo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhoto() {
        return photo;
    }

    public MateTalkRoom() {
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public List<Artist> getMatching_artists() {
        return matching_artists;
    }

    public void setMatching_artists(List<Artist> matching_artists) {
        this.matching_artists = matching_artists;
    }

    public int getPrefer_location() {
        return prefer_location;
    }

    public void setPrefer_location(int prefer_location) {
        this.prefer_location = prefer_location;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<User> getMember() {
        return member;
    }

    public void setMember(List<User> member) {
        this.member = member;
    }

    public MateTalkRoom(String name, String content, String number, String date, String unRead) {

        this.festival_name = name;
        this.content = content;
        this.number = number;
        this.date = date;
        this.unRead = unRead;
    }

    public MateTalkRoom(String title, int photo, String name, List<Artist> matching_artists, int prefer_location, int age, List<User> member) {
        this.title = title;
        this.photo = photo;
        this.festival_name = name;
        this.matching_artists = matching_artists;
        this.prefer_location = prefer_location;
        this.age = age;
        this.member = member;
    }

    List<Artist> matching_artists;
    int prefer_location;
    int age;
    List<User> member;

    public String getFestival_name() {
        return festival_name;
    }

    public void setFestival_name(String festival_name) {
        this.festival_name = festival_name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUnRead() {
        return unRead;
    }

    public void setUnRead(String unRead) {
        this.unRead = unRead;
    }

    private String unRead;

}
