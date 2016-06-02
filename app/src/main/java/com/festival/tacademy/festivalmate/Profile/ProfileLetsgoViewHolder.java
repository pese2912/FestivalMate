package com.festival.tacademy.festivalmate.Profile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.R;


/**
 * Created by J.K.Lee on 2016-05-19.
 */
public class ProfileLetsgoViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public ProfileLetsgoViewHolder(View itemView) {
        super(itemView);
        textView = (TextView)itemView.findViewById(R.id.text_profile_letsgo);
    }

    public void setText(String str) {
        textView.setText("-"+str);
    }
}
