package com.festival.tacademy.festivalmate.MyPage;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.Lineup;
import com.festival.tacademy.festivalmate.Data.MateTalkWaitList;
import com.festival.tacademy.festivalmate.Data.PreferenceArtist;
import com.festival.tacademy.festivalmate.Data.RequestChatroomJoinResult;
import com.festival.tacademy.festivalmate.Data.ShowWaitingListResult;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.Preference.PreferenceViewHolder;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class JoinWaitListActivity extends AppCompatActivity {

    RecyclerView listView;
    JoinWaitListAdapter mAdapter;
    Toolbar toolbar;
    FrameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_wait_list);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_navigation_back);

        layout = (FrameLayout)findViewById(R.id.non_wait_list);

        listView = (RecyclerView)findViewById(R.id.rv_list);
        mAdapter = new JoinWaitListAdapter();
        mAdapter.setOnItemClickListener(new JoinWaitListViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final MateTalkWaitList list) {
                //Toast.makeText(JoinWaitListActivity.this, list.getChatroom_name(), Toast.LENGTH_SHORT).show();
                int memNo = PropertyManager.getInstance().getNo();
                int chatroomNo = list.getChatroom_no();
                int state =1;
                list.setMem_chatroom_state(1);

                NetworkManager.getInstance().request_chatroom_join(JoinWaitListActivity.this, memNo, chatroomNo, state, new NetworkManager.OnResultListener<RequestChatroomJoinResult>() {
                    @Override
                    public void onSuccess(Request request, RequestChatroomJoinResult result) {
                     //   Toast.makeText(JoinWaitListActivity.this, "성공", Toast.LENGTH_SHORT).show();
                        list.setMem_chatroom_state(result.result.getMem_chatroom_state());
                        initData();
                    }

                    @Override
                    public void onFail(Request request, IOException exception) {
                  //      Toast.makeText(JoinWaitListActivity.this, "실패"+exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
     //   initData();
    }

    private void initData(){


        mAdapter.clear();
        int memNo = PropertyManager.getInstance().getNo();
        NetworkManager.getInstance().show_waiting_list(JoinWaitListActivity.this, memNo, new NetworkManager.OnResultListener<ShowWaitingListResult>() {
            @Override
            public void onSuccess(Request request, ShowWaitingListResult result) {

             //   Toast.makeText(JoinWaitListActivity.this, "성공"+result.result.size(), Toast.LENGTH_SHORT).show();
                mAdapter.addAll(result.result);
                if(result.result.size() == 0 || result.result ==null){
                    layout.setVisibility(View.VISIBLE);

                }

                else{
                    layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
             //   Toast.makeText(JoinWaitListActivity.this, "실패"+exception.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

//        for(int i = 0; i< 20; i++){
//            MateTalkWaitList list = new MateTalkWaitList();
//            list.setChatroom_festival("festival " + i);
//            list.setChatroom_name("name " + i);
//            list.setChatroom_img("http://sclibrary.net/files/attach/images/147/142/041/06b369a3aca7397d87a4505e638bd8ac.jpg");
//            List<Lineup> lineupList = new ArrayList<>();
//            Lineup lineup = new Lineup("date  " + i);
//            lineupList.add(lineup);
//            list.setFestival_lineups(lineupList);
//            list.setChatroom_wait_num(i);
//            mAdapter.add(list);
//        }
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
