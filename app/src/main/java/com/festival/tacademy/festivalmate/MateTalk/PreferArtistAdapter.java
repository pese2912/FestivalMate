package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-31.
 */
public class PreferArtistAdapter extends RecyclerView.Adapter<PreferArtistViewHolder> {

    List<Artist> items = new ArrayList<>();

    public void setItems(List<Artist> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void add(Artist item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<Artist> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public PreferArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user, null);

        return new PreferArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PreferArtistViewHolder holder, int position) {
        holder.setData(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
