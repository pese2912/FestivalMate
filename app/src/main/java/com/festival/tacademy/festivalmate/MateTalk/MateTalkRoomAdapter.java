package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.Preference.PreferenceViewHolder;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-16.
 */
public class MateTalkRoomAdapter extends RecyclerView.Adapter<MateTalkRoomViewHolder>{
    List<MateTalkRoom> items = new ArrayList<MateTalkRoom>();


    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }
    public void add(MateTalkRoom mateTalkRoom){
        items.add(mateTalkRoom);
        notifyDataSetChanged();
    }
    public void addAll(List<MateTalkRoom> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    MateTalkRoomViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(MateTalkRoomViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public MateTalkRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.view_matetalk_chattroom, null);
        return new MateTalkRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MateTalkRoomViewHolder holder, int position) {
        holder.setMateTalkRoom(items.get(position));
        holder.setOnItemClickListener(mListener);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
