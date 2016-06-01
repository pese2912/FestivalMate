package com.festival.tacademy.festivalmate.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-19.
 */

public class MateTalkWaitJoinList {

    public int is_host;
    public int chatroom_no;
    public String chatroom_name;
    public List<chatroom_member> chatroom_members;
    public List<chatroom_waiting> chatroom_waiting;
    public String festival_name;
    public String festival_location;
    public List<Lineup> festival_lineups = new ArrayList<>();



    public List<Artist> chatroom_lineups = new ArrayList<>();


    public List<Artist> getChatroom_lineups() {
        return chatroom_lineups;
    }

    public void setChatroom_lineups(List<Artist> chatroom_lineups) {
        this.chatroom_lineups = chatroom_lineups;
    }
    public int getChatroom_no() {
        return chatroom_no;
    }

    public void setChatroom_no(int chatroom_no) {
        this.chatroom_no = chatroom_no;
    }

    public String getName() {
        return chatroom_name;
    }

    public void setName(String name) {
        this.chatroom_name = name;
    }

    public List<chatroom_member> getChatroom_members() {
        return chatroom_members;
    }

    public void setChatroom_members(List<chatroom_member> chatroom_members) {
        this.chatroom_members = chatroom_members;
    }

    public List<chatroom_waiting> getChatroom_waitings() {
        return chatroom_waiting;
    }

    public void setChatroom_waitings(List<chatroom_waiting> chatroom_waitings) {
        this.chatroom_waiting = chatroom_waitings;
    }

    public int getIs_host() {
        return is_host;
    }

    public void setIs_host(int is_host) {
        this.is_host = is_host;
    }



    public String getFestival_name() {
        return festival_name;
    }

    public void setFestival_name(String festival_name) {
        this.festival_name = festival_name;
    }

    public String getFestival_location() {
        return festival_location;
    }

    public void setFestival_location(String festival_location) {
        this.festival_location = festival_location;
    }

    public List<Lineup> getFestival_lineups() {
        return festival_lineups;
    }

    public void setFestival_lineups(List<Lineup> festival_lineups) {
        this.festival_lineups = festival_lineups;
    }


}
