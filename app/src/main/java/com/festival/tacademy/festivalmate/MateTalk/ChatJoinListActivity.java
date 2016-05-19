package com.festival.tacademy.festivalmate.MateTalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.MateTalkWaitJoinList;
import com.festival.tacademy.festivalmate.Data.chatroom_waiting;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

public class ChatJoinListActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView toolbarTitle;


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
        toolbarTitle.setText(title);
        setData();

    }

    private void setData(){
        MateTalkWaitJoinList list = new MateTalkWaitJoinList();

        List<chatroom_waiting> waitingList = new ArrayList<>();
        for(int i=0; i < 3; i++){
            
        }
        for(int i=0; i < 5; i++){

        }


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
