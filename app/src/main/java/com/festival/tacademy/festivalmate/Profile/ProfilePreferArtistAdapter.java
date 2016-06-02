package com.festival.tacademy.festivalmate.Profile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-19.
 */
public class ProfilePreferArtistAdapter extends RecyclerView.Adapter<ProfilePreferArtistViewHolder> {

    List<Artist> items = new ArrayList<>();

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
    }

    @Override
    public ProfilePreferArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user, null);
        return new ProfilePreferArtistViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ProfilePreferArtistViewHolder holder, int position) {
        holder.setArtist(items.get(position));
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
