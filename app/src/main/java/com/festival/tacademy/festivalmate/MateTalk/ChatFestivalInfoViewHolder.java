package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by J.K.Lee on 2016-05-31.
 */
public class ChatFestivalInfoViewHolder extends RecyclerView.ViewHolder {

    TextView text_title, text_date, text_location;

    public ChatFestivalInfoViewHolder(View itemView) {
        super(itemView);
        text_title = (TextView)itemView.findViewById(R.id.text_festival_name);
        text_date = (TextView)itemView.findViewById(R.id.text_festival_date);
        text_location = (TextView)itemView.findViewById(R.id.text_festival_date);
    }

    public void setText(String str1, String str2, String str3) {
        text_title.setText(str1);
        text_date.setText(str2);
        text_location.setText(str3);
    }
}
