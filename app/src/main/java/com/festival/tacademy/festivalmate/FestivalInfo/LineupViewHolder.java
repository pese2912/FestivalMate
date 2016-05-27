package com.festival.tacademy.festivalmate.FestivalInfo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.festival.tacademy.festivalmate.Data.Lineup;
import com.festival.tacademy.festivalmate.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-16.
 */
public class LineupViewHolder extends RecyclerView.ViewHolder {

    TextView textDate, textLineup;

    public LineupViewHolder(View itemView) {
        super(itemView);
        textDate = (TextView)itemView.findViewById(R.id.text_date);
        textLineup = (TextView)itemView.findViewById(R.id.text_lineup);
    }

    public void setText(Lineup lineup) {
        textDate.setText(lineup.getDate());
        if(lineup.getLineup()!=null) {
            if (lineup.getLineup().size() != 0 && lineup != null) {
                textLineup.setText(lineup.getLineup().get(0).getName());
                for (int i = 1; i < lineup.getLineup().size(); i++) {
                    textLineup.append(", " + lineup.getLineup().get(i).getName());
                }
            }
        }
    }
}
