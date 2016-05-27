package com.festival.tacademy.festivalmate.MateMatching;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-17.
 */
public class MateMatchingLineUpArtistAdapter extends RecyclerView.Adapter<MateMatchingLineUpArtistViewHolder> {

    List<Artist> items = new ArrayList<Artist>();

    public void add(Artist artist){
        this.items.add(artist);
        notifyDataSetChanged();
    }


    public void addAll(List<Artist> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clear(){
        this.items.clear();
        notifyDataSetChanged();
    }

    @Override
    public MateMatchingLineUpArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.view_mate_matching_lineup_artist, null);
        return new MateMatchingLineUpArtistViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MateMatchingLineUpArtistViewHolder holder, int position) {
        holder.setFestibalLineUpArtist(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
