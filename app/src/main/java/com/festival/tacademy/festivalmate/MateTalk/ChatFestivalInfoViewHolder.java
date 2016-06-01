package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.Data.Lineup;
import com.festival.tacademy.festivalmate.R;

import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-31.
 */
public class ChatFestivalInfoViewHolder extends RecyclerView.ViewHolder {

    TextView text_title, text_date, text_location;

    public ChatFestivalInfoViewHolder(View itemView) {
        super(itemView);
        text_title = (TextView)itemView.findViewById(R.id.text_festival_name);
        text_date = (TextView)itemView.findViewById(R.id.text_festival_date);
        text_location = (TextView)itemView.findViewById(R.id.text_festival_location);
    }

    public void setText(String title, List<Lineup> date, String location) {
        text_title.setText(title);
        text_date.setText("-기간: "+date.get(0).getDate()+"-"+date.get(date.size()-1).getDate());
        text_location.setText("-장소:"+location);
    }
}
