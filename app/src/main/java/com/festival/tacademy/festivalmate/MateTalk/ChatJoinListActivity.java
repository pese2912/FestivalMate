package com.festival.tacademy.festivalmate.MateTalk;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.MateTalkWaitJoinList;
import com.festival.tacademy.festivalmate.Data.chatroom_member;
import com.festival.tacademy.festivalmate.Data.chatroom_waiting;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

public class ChatJoinListActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTitle;
    RecyclerView listView;
    ChatJoinListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_join_list);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String title = intent.getStringExtra("joinList");

        listView = (RecyclerView)findViewById(R.id.rv_list);
        mAdapter= new ChatJoinListAdapter();
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        setData();

    }

    private void setData(){
        MateTalkWaitJoinList list = new MateTalkWaitJoinList();

        List<chatroom_waiting> waitingList = new ArrayList<>();
        for(int i=0; i < 2; i++){

            chatroom_waiting waiting = new chatroom_waiting();
            waiting.setMem_name("waitName" + i);
            waiting.setMem_img(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
            waitingList.add(waiting);
        }
        list.setChatroom_waitings(waitingList);

        List<chatroom_member> memberList = new ArrayList<>();
        for(int i=0; i < 3; i++){

            chatroom_member member = new chatroom_member();
            member.setMem_name("joinName" + i);
            member.setMem_img(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
            memberList.add(member);
        }

        list.setChatroom_members(memberList);
        list.setName(toolbarTitle.getText().toString());

        mAdapter.setMateTalkWaitJoinList(list);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
