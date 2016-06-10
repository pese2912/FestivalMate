package com.festival.tacademy.festivalmate.MateMatching;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.festival.tacademy.festivalmate.Data.MateTalkRoom;
import com.festival.tacademy.festivalmate.FestivalInfo.UserViewHolder;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by J.K.Lee on 2016-05-18.
 */
public class MatetalkDetailAdapter extends RecyclerView.Adapter<MatetalkDetailViewHolder> {

    List<MateTalkRoom> items = new ArrayList<>();
    
    public void add(MateTalkRoom festival) {
        items.add(festival);
        notifyDataSetChanged();
    }
    
    public void addAll(List<MateTalkRoom> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }
    
    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    MatetalkDetailViewHolder.OnItemClickListener mListener;
    ChatUserViewHolder.OnItemClickListener mListener2;

    public void setOnItemClickListener(MatetalkDetailViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnItemClickListener(ChatUserViewHolder.OnItemClickListener listener) {
        mListener2 = listener;
    }


    @Override
    public MatetalkDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_matetalk_detail_item, null);

        return new MatetalkDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatetalkDetailViewHolder holder, int position) {
        holder.setMateTalkRoom(items.get(position), mListener2);
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getItemPosition(int roomNo) {
        for(int i=0; i<items.size(); i++) {
            if(roomNo == items.get(i).getChatroom_no()) {
                return i;
            }
        }
        return 0;
    }


}

