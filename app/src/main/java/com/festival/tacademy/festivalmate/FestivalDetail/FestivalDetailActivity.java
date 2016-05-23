package com.festival.tacademy.festivalmate.FestivalDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.MateMatching.MateMatchingActivity;
import com.festival.tacademy.festivalmate.Profile.ProfileDialogFragment;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;


public class FestivalDetailActivity extends AppCompatActivity {

    RecyclerView listView;
    FestivalDetailAdapter mAdapter;
    LinearLayoutManager mManager;
    Festival festival;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival_detail);

        listView = (RecyclerView)findViewById(R.id.rv_list);
        mAdapter = new FestivalDetailAdapter();
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(mManager);

        Intent intent = getIntent();
        festival = (Festival)intent.getExtras().getSerializable("festival");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdapter.setFestval(festival);

        Toast.makeText(this, festival.getName() + "+ " + festival.getDate() + "+ " + festival.getLocation(), Toast.LENGTH_SHORT).show();

        Button btn = (Button)findViewById(R.id.btn_matching);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FestivalDetailActivity.this, MateMatchingActivity.class));
            }
        });

        mAdapter.setOnItemClickListener(new UserViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                ProfileDialogFragment f = new ProfileDialogFragment();

                List<Artist> artists = new ArrayList<>();
                List<Festival> letsgo = new ArrayList<>();

                for(int i=0; i<3; i++) {
                    letsgo.add(new Festival("Festival "+i));
                }

                for(int i=0; i<10; i++) {
                    artists.add(new Artist("Artist: " + i, R.drawable.back1));
                }

                User user = new User("ID " + 1, R.drawable.face,"Name " + 1, letsgo, artists);


                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                f.setArguments(bundle);
                f.show(getSupportFragmentManager(), "aaaa");
            }
        });


    }

}
