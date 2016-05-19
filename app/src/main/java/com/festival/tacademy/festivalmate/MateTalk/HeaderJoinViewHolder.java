package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.R;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class HeaderJoinViewHolder extends RecyclerView.ViewHolder {

    TextView headerView;

    public HeaderJoinViewHolder(View itemView) {
        super(itemView);
        headerView = (TextView) itemView.findViewById(R.id.text_title);
    }
}
