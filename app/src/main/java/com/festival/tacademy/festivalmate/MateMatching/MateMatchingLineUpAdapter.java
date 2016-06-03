package com.festival.tacademy.festivalmate.MateMatching;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.festival.tacademy.festivalmate.Data.FestibalLineUp;
import com.festival.tacademy.festivalmate.Data.Lineup;
import com.festival.tacademy.festivalmate.Preference.PreferenceViewHolder;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-17.
 */

public class MateMatchingLineUpAdapter extends RecyclerView.Adapter<MateMatchingLineUpViewHolder> {
    List<Lineup> items = new ArrayList<Lineup>();

    public void add(Lineup lineUp){
        this.items.add(lineUp);
        notifyDataSetChanged();
    }
    public void addAll(List<Lineup> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }
    public void clear(){
        this.items.clear();
        notifyDataSetChanged();
    }


    @Override
    public MateMatchingLineUpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.view_mate_matching_lineup, null);
        return new MateMatchingLineUpViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MateMatchingLineUpViewHolder holder, int position) {
        holder.setFestibalLineUp(items.get(position));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
