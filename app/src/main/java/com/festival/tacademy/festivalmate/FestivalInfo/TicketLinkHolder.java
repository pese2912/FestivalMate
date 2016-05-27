package com.festival.tacademy.festivalmate.FestivalInfo;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by J.K.Lee on 2016-05-27.
 */
public class TicketLinkHolder extends RecyclerView.ViewHolder {

    TextView textView;
    public TicketLinkHolder(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.text_ticket_link);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(textView.getText().toString());
            }
        });
    }
    public interface OnItemClickListner {
        public void onItemClick(String str);
    }
    OnItemClickListner mListener;

    public void setOnItemClickListener(OnItemClickListner listener) {
        mListener = listener;
    }

    public void setText(Festival festival) {
        textView.setText(festival.getFestival_ticket_link());
    }
}
