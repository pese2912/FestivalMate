package com.festival.tacademy.festivalmate.Profile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-19.
 */
public class ProfileLetsgoAdapter extends RecyclerView.Adapter<ProfileLetsgoViewHolder> {

    List<Festival> items = new ArrayList<>();

    public void add(Festival festival) {
        items.add(festival);
        notifyDataSetChanged();
    }

    public void addAll(List<Festival> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
    }

    @Override
    public ProfileLetsgoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_profile_letsgo, null);

        return new ProfileLetsgoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProfileLetsgoViewHolder holder, int position) {
        holder.setText(items.get(position).getFestival_name());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
