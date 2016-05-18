package com.festival.tacademy.festivalmate.MateMatching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.Data.FestibalLineUp;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

public class MateMatchingStartActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView listView;
    MateMatchingLineUpAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mate_matching_start);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (RecyclerView)findViewById(R.id.rv_list);
        mAdapter = new MateMatchingLineUpAdapter();
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
        initData();

     Button   btn = (Button)findViewById(R.id.btn_match);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MateMatchingStartActivity.this, MateMatchingActivity.class));
            }
        });
    }
    private void initData(){



            List<Artist> artistList = new ArrayList<>();
            for(int j=0; j<10; j++) {
                Artist artist = new Artist("김창완밴드");
                artist.setName("김창완밴드");
                artistList.add(artist);
            }

            for (int i = 0; i < 3; i++) {
                FestibalLineUp festibalLineUp = new FestibalLineUp();
                festibalLineUp.setData("2016-07-24");
                festibalLineUp.setArtist(artistList);
                mAdapter.add(festibalLineUp);
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
