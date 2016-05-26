package com.festival.tacademy.festivalmate.MateMatching;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.FestivalInfo.UserViewHolder;
import com.festival.tacademy.festivalmate.Profile.ProfileDialogFragment;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

public class MateMatchingActivity extends AppCompatActivity {

    Toolbar toolbar;
    boolean isClicked = false;
    RecyclerView listView;
    MatetalkDetailAdapter mAdapter;
    LinearLayoutManager mManager;
    boolean isClick = false;

    List<Artist> artists = new ArrayList<>();
    List<User> users = new ArrayList<>();
    List<MateTalkRoom> chatinfoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mate_matching);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listView = (RecyclerView)findViewById(R.id.rv_list);
        mAdapter = new MatetalkDetailAdapter();
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        listView.setAdapter(mAdapter);
        listView.setLayoutManager(mManager);

        initData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_make_talk, menu);
         return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.make_talk){
            startActivity(new Intent(MateMatchingActivity.this, MakeMateTalkActivity.class));

            return true;
        }
       else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void initData() {
        for (int i = 0; i < 10; i++) {

            Artist artist = new Artist();
            artist.setMatched_artist_name("matchedName : "+ i);
            artist.setMatched_artist_no(i);
            artists.add(artist);

            User user = new User();
            user.setChatroom_mem_name("chatName : "+ i);
            user.setChatroom_mem_img("http://sitehomebos.kocca.kr/knowledge/abroad/deep/__icsFiles/artimage/2012/03/26/2_1.jpg");
            user.setChatroom_mems_no(i);
            users.add(user);

        }

        for( int i=0; i<10; i++ ) {
            chatinfoes.add(new MateTalkRoom("모두모두 대환영 " + i,"http://sitehomebos.kocca.kr/knowledge/abroad/deep/__icsFiles/artimage/2012/03/26/2_1.jpg" , "Festival " + i, artists, 1, 1, users));
        }


        mAdapter.addAll(chatinfoes);
        mAdapter.setOnItemClickListener(new MatetalkDetailViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                View v = view.findViewById(R.id.detail_view);

                if(isClick == false) {
                    v.setVisibility(View.VISIBLE);
                    isClick = true;
                } else {
                    v.setVisibility(View.GONE);
                    isClick = false;
                }
            }
        });

        mAdapter.setOnItemClickListener(new ChatUserViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                ProfileDialogFragment f = new ProfileDialogFragment();

                List<Artist> artists = new ArrayList<>();
                List<Festival> letsgo = new ArrayList<>();

                for(int i=0; i<3; i++) {
                    letsgo.add(new Festival("Festival "+i));
                }

                for(int i=0; i<10; i++) {
                    artists.add(new Artist("Artist: " + i, "http://sitehomebos.kocca.kr/knowledge/abroad/deep/__icsFiles/artimage/2012/03/26/2_1.jpg"));
                }
                User user = new User("ID " + 1, "http://sitehomebos.kocca.kr/knowledge/abroad/deep/__icsFiles/artimage/2012/03/26/2_1.jpg","Name " + 1, letsgo, artists);

                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                f.setArguments(bundle);
                f.show(getSupportFragmentManager(), "aaaa");
            }
        });
    }

}
