package com.festival.tacademy.festivalmate.Preference;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.festival.tacademy.festivalmate.Data.PreferenceArtist;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-13.
 */
public class PreferenceAdapter extends RecyclerView.Adapter<PreferenceViewHolder>{
   List<PreferenceArtist> items = new ArrayList<PreferenceArtist>();

    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public void add(PreferenceArtist artist){
        items.add(artist);
        notifyDataSetChanged();

    }
    public void addAll(List<PreferenceArtist> artists){
        items.addAll(artists);
        notifyDataSetChanged();
    }



    @Override
    public PreferenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.view_prefer_search, null);
        return new PreferenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PreferenceViewHolder holder, int position) {
        holder.setPreferenceArtist(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
