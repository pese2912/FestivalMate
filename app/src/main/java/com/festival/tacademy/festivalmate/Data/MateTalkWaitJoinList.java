package com.festival.tacademy.festivalmate.Data;

import java.util.List;

/**
 * Created by Tacademy on 2016-05-19.
 */

public class MateTalkWaitJoinList {

    public int getChatroom_no() {
        return chatroom_no;
    }

    public void setChatroom_no(int chatroom_no) {
        this.chatroom_no = chatroom_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<chatroom_member> getChatroom_members() {
        return chatroom_members;
    }

    public void setChatroom_members(List<chatroom_member> chatroom_members) {
        this.chatroom_members = chatroom_members;
    }

    public List<chatroom_waiting> getChatroom_waitings() {
        return chatroom_waitings;
    }

    public void setChatroom_waitings(List<chatroom_waiting> chatroom_waitings) {
        this.chatroom_waitings = chatroom_waitings;
    }

    public int chatroom_no;
    public String name;
    List<chatroom_member> chatroom_members;
    List<chatroom_waiting> chatroom_waitings;

}
