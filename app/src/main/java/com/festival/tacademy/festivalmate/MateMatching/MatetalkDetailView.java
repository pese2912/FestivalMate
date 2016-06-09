package com.festival.tacademy.festivalmate.MateMatching;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.FestivalInfo.UserAdapter;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;


/**
 * Created by J.K.Lee on 2016-05-18.
 */
public class MatetalkDetailView extends RelativeLayout {

    MateTalkRoom chatinfo=null;
    boolean isClicked = false;

    public MateTalkRoom getMateTalkRoom() {
        return chatinfo;
    }


    public MatetalkDetailView(Context context) {
        super(context);
        init();
    }

    public MatetalkDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    TextView text_title, text_artist_num, text_artists, text_region, text_age, text_mem_user;
    Button btn;

    RecyclerView listView;
    ChatUserAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_matetalk_detail, this);

        text_title = (TextView) findViewById(R.id.text_title);
        text_artist_num = (TextView) findViewById(R.id.text_artist_num);
        text_artists = (TextView) findViewById(R.id.text_artists);
        text_region = (TextView) findViewById(R.id.text_region);
        text_age = (TextView) findViewById(R.id.text_age);
        text_mem_user = (TextView) findViewById(R.id.text_mem_user);
        btn = (Button) findViewById(R.id.btn_request);

        listView = (RecyclerView) findViewById(R.id.rv_list);
        mAdapter = new ChatUserAdapter(getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(mLayoutManager);

        if( chatinfo == null ) {
            text_title.setText("DDDDD");
            return;
        }

        text_artist_num.setText(chatinfo.getMatched_artist_number() + "명");

                text_artists.setText(chatinfo.getMatched_artist().get(0).getMatched_artist_name());
        for (int j = 1; j < chatinfo.getMatched_artist().size(); j++) {
            text_artists.append(", " + chatinfo.getMatched_artist().get(j).getMatched_artist_name());
        }

        text_region.setText(chatinfo.getChatroom_location() + "");
        text_age.setText(chatinfo.getChatroom_age() + "");

        text_mem_user.setText(chatinfo.getChatroom_size() + "명");
//        Button btn = (Button)findViewById(R.id.btn_request);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MyApplication.getContext(),chatinfo.getChatroom_name() , Toast.LENGTH_SHORT);
//            }
//        });

        // mAdapter.addAll(chatinfo.getChatroom_mems());
    }
}


