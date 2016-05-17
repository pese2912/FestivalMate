package com.festival.tacademy.festivalmate.FestivalInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Tacademy on 2016-05-16.
 */
public class FestivalAdapter extends RecyclerView.Adapter<FestivalViewHolder> {

    List<Festival> items = new ArrayList<Festival>();

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
        notifyDataSetChanged();
    }

    FestivalViewHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(FestivalViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public FestivalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_my_festival_item, parent, false);

        return new FestivalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FestivalViewHolder holder, int position) {
        holder.setFestival(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
