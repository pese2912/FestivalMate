package com.festival.tacademy.festivalmate.FestivalInfo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

import java.util.BitSet;

/**
 * Created by J.K.Lee on 2016-05-27.
 */
public class TicketLinkHolder extends RecyclerView.ViewHolder {

    TextView ticketView;
    TextView homepageView;
    Button btn_facebook;
    Button btn_twitter;


    Festival festival;

    public TicketLinkHolder(View itemView) {
        super(itemView);
        ticketView = (TextView)itemView.findViewById(R.id.text_ticket_link);
        homepageView = (TextView)itemView.findViewById(R.id.text_homepage);
        btn_facebook = (Button)itemView.findViewById(R.id.btn_facebook);
        btn_twitter = (Button)itemView.findViewById(R.id.btn_twitter);

        ticketView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(festival.getFestival_ticket_link());
            }
        });
        homepageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick2(festival.getFestival_homepage());
            }
        });
        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick3("https://www.facebook.com/");
            }
        });
        btn_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick4("http://m.twitter.com/");
            }
        });
    }

    public interface OnItemClickListner {
        public void onItemClick(String str);
        public void onItemClick2(String str);
        public void onItemClick3(String str);
        public void onItemClick4(String str);
    }
    OnItemClickListner mListener;

    public void setOnItemClickListener(OnItemClickListner listener) {
        mListener = listener;
    }

    public void setText(Festival festival) {
        this.festival = festival;
     //   textView.setText(festival.getFestival_ticket_link());
    }
}
