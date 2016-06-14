package com.festival.tacademy.festivalmate.MateTalk;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.Data.PreferenceArtist;
import com.festival.tacademy.festivalmate.Data.RequestNewChatResult;
import com.festival.tacademy.festivalmate.Data.ShowMyChatroomListResult;
import com.festival.tacademy.festivalmate.GCM.MyGcmListenerService;
import com.festival.tacademy.festivalmate.Manager.NetworkManager;
import com.festival.tacademy.festivalmate.Manager.PropertyManager;
import com.festival.tacademy.festivalmate.MateMatching.MakeMateTalkActivity;
import com.festival.tacademy.festivalmate.Preference.PreferenceAdapter;
import com.festival.tacademy.festivalmate.Preference.PreferenceViewHolder;
import com.festival.tacademy.festivalmate.R;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class MateTalkFragment extends Fragment {

    TextView titleView;
    RecyclerView listView;
    MateTalkRoomAdapter mAdapter;
    FrameLayout non_matetalk;

    public MateTalkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MateTalkRoomAdapter();

        mAdapter.setOnItemClickListener(new MateTalkRoomViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MateTalkRoom talkRoom) {
                Toast.makeText(getContext(), talkRoom.getChatroom_festival_name(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ChattingActivity.class);
                intent.putExtra("chatting",talkRoom);
                startActivity(intent);
            }
        });

        mAdapter.setOnItemClickListener(new MateTalkRoomSingleViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MateTalkRoom talkRoom) {
               // Toast.makeText(getContext(), talkRoom.getChatroom_festival_name(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ChattingActivity.class);
                intent.putExtra("chatting",talkRoom);
                intent.putExtra(MyGcmListenerService.EXTRA_SENDER_RESULT, new RequestNewChatResult());
                startActivity(intent);
            }
        });
        setData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mate_talk, container, false);

        titleView = (TextView) getActivity().findViewById(R.id.toolbar_title);
        titleView.setText(getResources().getString(R.string.mate_talk));

        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        non_matetalk = (FrameLayout)view.findViewById(R.id.matetalk_size0);
        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setData(){

        final int memNo = PropertyManager.getInstance().getNo();

        NetworkManager.getInstance().show_my_chatroom_list(getContext(), memNo, new NetworkManager.OnResultListener<ShowMyChatroomListResult>() {
            @Override
            public void onSuccess(Request request, ShowMyChatroomListResult result) {
             //   Toast.makeText(getContext(),"성공"+memNo,Toast.LENGTH_SHORT).show();
                mAdapter.clear();
                mAdapter.addAll(result.result);


                if(result.result.size()==0 || result.result == null || memNo == 0){
                    non_matetalk.setVisibility(View.VISIBLE);
                }

                else{
                    non_matetalk.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(Request request, IOException exception) {
           //     Toast.makeText(getContext(),"실패"+exception.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
//        for (int i = 0; i < 20; i++) {
//            MateTalkRoom room = new MateTalkRoom();
//            room.setChatroom_festival_name("name " + i);
//       //     room.setChatroom_img(R.mipmap.ic_launcher);
//            room.setChatroom_name("hey, 모두들 안녕~ 내가누군지 아니??");
//            room.date="7월 24일";
//            room.setChatroom_no(i);
//            room.setMatched_artist_number(i);
//            room.setUnRead(i+"");
//            mAdapter.add(room); // 값 추가
//        }
    }
}

/*
Chatting User Table
id, serverid,name,email

Chatting Table
_id,uid,type,message,date
 */

