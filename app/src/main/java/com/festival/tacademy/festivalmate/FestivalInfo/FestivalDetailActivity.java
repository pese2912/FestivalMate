package com.festival.tacademy.festivalmate.FestivalInfo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.MateMatching.MateMatchingStartActivity;
import com.festival.tacademy.festivalmate.R;

public class FestivalDetailActivity extends AppCompatActivity {

    TextView toolbarTitle;
    Toolbar toolbar;
    RecyclerView listView;
    FestivalDetailAdapter mAdapter;
    LinearLayoutManager mManager;
    Festival festival;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival_detail);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        fab = (FloatingActionButton)findViewById(R.id.btn_matching_start);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (RecyclerView)findViewById(R.id.rv_list);
        mAdapter = new FestivalDetailAdapter();
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(mManager);

        Intent intent = getIntent();
        festival = (Festival)intent.getExtras().getSerializable("festival");
        toolbarTitle.setText(festival.getName());
        mAdapter.setFestval(festival);

        Toast.makeText(this, festival.getName() + "+ " + festival.getDate() + "+ " + festival.getLocation(), Toast.LENGTH_SHORT).show();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FestivalDetailActivity.this, MateMatchingStartActivity.class);
                startActivity(intent);
            }
        });
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
