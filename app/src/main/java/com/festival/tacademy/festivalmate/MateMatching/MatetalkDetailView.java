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
import com.festival.tacademy.festivalmate.FestivalDetail.UserAdapter;
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
    RecyclerView listView;
    UserAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_matetalk_detail, this);

        text_title = (TextView) findViewById(R.id.text_title);
        text_artist_num = (TextView) findViewById(R.id.text_artist_num);
        text_artists = (TextView) findViewById(R.id.text_artists);
        text_region = (TextView) findViewById(R.id.text_region);
        text_age = (TextView) findViewById(R.id.text_age);
        text_mem_user = (TextView) findViewById(R.id.text_mem_user);

        listView = (RecyclerView) findViewById(R.id.rv_list);
        mAdapter = new UserAdapter(getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listView.setAdapter(mAdapter);
        listView.setLayoutManager(mLayoutManager);

        if( chatinfo == null ) {
            text_title.setText("DDDDD");
            return;
        }


        text_artist_num.setText(chatinfo.getMatching_artists().size() + "명");

        text_artists.setText(chatinfo.getMatching_artists().get(0).getName());
        for (int j = 1; j < chatinfo.getMatching_artists().size(); j++) {
            text_artists.append(", " + chatinfo.getMatching_artists().get(j).getName());
        }

        text_region.setText(chatinfo.getPrefer_location() + "");
        text_age.setText(chatinfo.getAge() + "");

        text_mem_user.setText(chatinfo.getMember().size() + "명");

        Button btn = (Button)findViewById(R.id.btn_request);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button btn = (Button)findViewById(R.id.btn_request);
                if(!isClicked) {
                    btn.setText("신청 완료");
                    Toast.makeText(MyApplication.getContext(), "dddddd", Toast.LENGTH_SHORT);
                    isClicked = true;
                } else {
                    btn.setText("신청하기");
                    Toast.makeText(MyApplication.getContext(), "ffffff", Toast.LENGTH_SHORT);
                    isClicked = false;
                }

            }
        });

          mAdapter.addAll(chatinfo.getMember());
    }
}


