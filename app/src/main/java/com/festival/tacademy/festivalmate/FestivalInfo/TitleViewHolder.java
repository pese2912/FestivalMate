package com.festival.tacademy.festivalmate.FestivalInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by J.K.Lee on 2016-05-16.
 */

public class TitleViewHolder extends RecyclerView.ViewHolder {

    TextView titleView;
    public TitleViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView)itemView.findViewById(R.id.text_menu);
    }

    public void setData(String title) {
        titleView.setText(title);
    }
}
