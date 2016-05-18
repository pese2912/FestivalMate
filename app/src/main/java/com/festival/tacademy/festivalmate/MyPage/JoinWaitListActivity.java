package com.festival.tacademy.festivalmate.MyPage;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.MateTalkWaitList;
import com.festival.tacademy.festivalmate.Data.PreferenceArtist;
import com.festival.tacademy.festivalmate.Preference.PreferenceViewHolder;
import com.festival.tacademy.festivalmate.R;

public class JoinWaitListActivity extends AppCompatActivity {

    RecyclerView listView;
    JoinWaitListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_wait_list);
        listView = (RecyclerView)findViewById(R.id.rv_list);
        mAdapter = new JoinWaitListAdapter();
        mAdapter.setOnItemClickListener(new JoinWaitListViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MateTalkWaitList list) {
                Toast.makeText(JoinWaitListActivity.this, list.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        initData();
    }

    private void initData(){
        for(int i = 0; i< 20; i++){
            MateTalkWaitList list = new MateTalkWaitList();
            list.setName("name " + i);
            list.setTitle("title " + i);
            list.setPhoto(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
            list.setDate("date " + i);
            list.setPeople( i+"명");
            list.setWaitPeople(i+"명");
            mAdapter.add(list);
        }
    }
}
