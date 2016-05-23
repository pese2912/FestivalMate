package com.festival.tacademy.festivalmate.MyPage;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.Lineup;
import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.FestivalInfo.FestivalAdapter;
import com.festival.tacademy.festivalmate.FestivalInfo.FestivalDetailActivity;
import com.festival.tacademy.festivalmate.FestivalInfo.FestivalViewHolder;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

public class FestibalSearchActivity extends AppCompatActivity {

    RecyclerView listView;
    Toolbar toolbar;
    FestivalAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festibal_search);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAdapter = new FestivalAdapter();
        listView = (RecyclerView)findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter.setOnItemClickListener(new FestivalViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Festival festival) {

                final  Festival festival1 = festival;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Toast.makeText(getContext(),festival.getName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FestibalSearchActivity.this, FestivalDetailActivity.class);
                        intent.putExtra("festival", festival1);
                        startActivity(intent);
                    }
                }, 250);
            }
        });

        Button btn= (Button)findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    private void initData() {
        mAdapter.clear();
        List<User> users = new ArrayList<>();
        List<Lineup> lineups = new ArrayList<>();
        List<Artist> artists = new ArrayList<>();

        for(int i=0; i<10; i++) {
            users.add(new User("User: " + i, R.mipmap.ic_launcher));
            artists.add(new Artist("Artist: " + i));
        }
        for(int i=0; i<3; i++) {
            lineups.add(new Lineup("Date: " + i, artists));
        }

        for (int i = 0; i < 10; i++) {
            mAdapter.add(new Festival("Item: "+i, "http://www.betanews.net/imagedb/thumb/2014/0627/7ec1b12a.jpg", "Date: "+i, "Location: "+i, users, lineups));
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
