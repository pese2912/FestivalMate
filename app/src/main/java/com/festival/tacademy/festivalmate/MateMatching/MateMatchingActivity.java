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
import android.widget.Button;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.Data.RequestChatroomJoinResult;
import com.festival.tacademy.festivalmate.Data.SelectedArtist;
import com.festival.tacademy.festivalmate.Data.ShowMatchingResult;
import com.festival.tacademy.festivalmate.Data.ShowMemProfileResult;
import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.FestivalInfo.UserViewHolder;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.Profile.ProfileDialogFragment;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MateMatchingActivity extends AppCompatActivity {

    Toolbar toolbar;
    boolean isClicked = false;
    RecyclerView listView;
    MatetalkDetailAdapter mAdapter;
    LinearLayoutManager mManager;
    boolean isClick = false;
    ShowMatchingResult result;
    SelectedArtist selectedArtist;

    Festival festival;

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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.btn_navigation_back);

        Intent intent = getIntent();
        result = (ShowMatchingResult)intent.getExtras().getSerializable("ShowMatchingResult");
        festival = (Festival) intent.getExtras().getSerializable("festival");
        selectedArtist = (SelectedArtist)intent.getExtras().getSerializable("selectedArtist");

        listView = (RecyclerView)findViewById(R.id.rv_list);
        mAdapter = new MatetalkDetailAdapter();
        mAdapter.setOnItemClickListener(new MatetalkDetailViewHolder.OnItemClickListener() { // 더보기

            @Override
            public void onItemClick(final View view, final MateTalkRoom room) {
                final View v = view.findViewById(R.id.detail_view);
                final int memNo = PropertyManager.getInstance().getNo();
               final int roomNo = room.getChatroom_no();
                final  int state = room.getMem_chatroom_state();

                final Button btn = (Button)v.findViewById(R.id.btn_request);




//                if(room.getMem_chatroom_state() == 2)
//                    btn.setText("참여중");
//                else if(room.getMem_chatroom_state() == 1)
//                    btn.setText("대기중");
//                else if(room.getMem_chatroom_state() == 0)
//                    btn.setText("메이트톡 시작");

                btn.setOnClickListener(new View.OnClickListener() {  // 버튼 클릭
                    @Override
                    public void onClick(View v) {
                       // Toast.makeText(MateMatchingActivity.this, memNo+ "  "+roomNo+"  "+state+"  " ,Toast.LENGTH_SHORT).show();

                        NetworkManager.getInstance().request_chatroom_join(MateMatchingActivity.this, memNo, roomNo, state,new NetworkManager.OnResultListener<RequestChatroomJoinResult>() {

                            @Override
                            public void onSuccess(Request request, RequestChatroomJoinResult result) {
                              //  Toast.makeText(MateMatchingActivity.this,result.result.getMem_chatroom_state()+"",Toast.LENGTH_SHORT).show();

                                if(result.result.getMem_chatroom_state() == 2) {
                                    room.setMem_chatroom_state(2);
                                    btn.setText("참여중");
                                }

                                else if(result.result.getMem_chatroom_state() == 1) {
                                    room.setMem_chatroom_state(1);
                                    btn.setText("신청 완료! 방장 승인을 기다려 주세요.");
                                }

                                else if(result.result.getMem_chatroom_state() == 0) {
                                    room.setMem_chatroom_state(0);
                                    btn.setText("메이트톡 시작");
                                }
                            }


                            @Override
                            public void onFail(Request request, IOException exception) {
                                Toast.makeText(MateMatchingActivity.this, "실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });


                if(isClick == false) {
                    v.setVisibility(View.VISIBLE);

                    mManager.scrollToPositionWithOffset(mAdapter.getItemPosition(roomNo), 0);
                    isClick = true;
                } else {
                    v.setVisibility(View.GONE);
                    isClick = false;
                }
            }
        });

        mAdapter.setOnItemClickListener(new ChatUserViewHolder.OnItemClickListener() {  // 프로필 조회
            @Override
            public void onItemClick(View view, User user) {

                NetworkManager.getInstance().show_mem_profile(MateMatchingActivity.this, user.getChatroom_mems_no(), new NetworkManager.OnResultListener<ShowMemProfileResult>() {
                    @Override
                    public void onSuccess(Request request, ShowMemProfileResult result) {
                      //  Toast.makeText(MateMatchingActivity.this, "성공",Toast.LENGTH_SHORT).show();
                        ProfileDialogFragment f = new ProfileDialogFragment();

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", result.result);
                        f.setArguments(bundle);
                        f.show(getSupportFragmentManager(), "aaaa");
                    }
                    
                    @Override
                    public void onFail(Request request, IOException exception) {
                    //    Toast.makeText(MateMatchingActivity.this, "실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        listView.setAdapter(mAdapter);
        listView.setLayoutManager(mManager);

        initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
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
            Intent intent = new Intent(MateMatchingActivity.this, MakeMateTalkActivity.class);
            intent.putExtra("festival",festival);
            intent.putExtra("selectedArtist",selectedArtist);
          //  Toast.makeText(MyApplication.getContext(),festival.getFestival_no()+"",Toast.LENGTH_SHORT).show();
            startActivity(intent);

            return true;
        }
       else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initData() {

        mAdapter.clear();
        mAdapter.addAll(result.result);


    }

}
