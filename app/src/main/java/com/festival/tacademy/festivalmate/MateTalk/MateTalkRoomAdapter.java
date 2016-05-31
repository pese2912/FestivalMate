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
public class MateTalkRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<MateTalkRoom> items = new ArrayList<MateTalkRoom>();

    private static final int VIEW_TYPE_COUNT =2;
    private static final int TYPE_SINGLE = 1;
    private static final int TYPE_FESTIVAL = 0;


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

    @Override
    public int getItemViewType(int position) {
        int type = items.get(position).getChatroom_style();
        if(type ==0){
            return TYPE_FESTIVAL;
        }

        return TYPE_SINGLE;

    }


    MateTalkRoomViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(MateTalkRoomViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    MateTalkRoomSingleViewHolder.OnItemClickListener mListener2;
    public void setOnItemClickListener(MateTalkRoomSingleViewHolder.OnItemClickListener listener) {
        mListener2 = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_FESTIVAL: //This would be the header view in my Recycler
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_matetalk_chattroom, null);
                return new MateTalkRoomViewHolder(view);

            case TYPE_SINGLE: //This would be the header view in my Recycler

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_matetalk_chattroom_single, null);
                return new MateTalkRoomSingleViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_FESTIVAL: //This would be the header view in my Recycler
                MateTalkRoomViewHolder h = (MateTalkRoomViewHolder)holder;
                h.setMateTalkRoom(items.get(position));
                h.setOnItemClickListener(mListener);
                return;

            case TYPE_SINGLE: //This would be the header view in my Recycler

                MateTalkRoomSingleViewHolder hd = (MateTalkRoomSingleViewHolder)holder;
                hd.setMateTalkRoom(items.get(position));
                hd.setOnItemClickListener(mListener2);
                return;
        }
    }

//    @Override
//    public void onBindViewHolder(MateTalkRoomViewHolder holder, int position) {
//        holder.setMateTalkRoom(items.get(position));
//        holder.setOnItemClickListener(mListener);
//
//
//    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
