package com.festival.tacademy.festivalmate.MyPage;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.festival.tacademy.festivalmate.Data.MateTalkWaitList;
import com.festival.tacademy.festivalmate.Preference.PreferenceViewHolder;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-18.
 */
public class JoinWaitListAdapter extends RecyclerView.Adapter<JoinWaitListViewHolder> {

    List<MateTalkWaitList> items = new ArrayList<MateTalkWaitList>();

    public void add(MateTalkWaitList item){
        this.items.add(item);
        notifyDataSetChanged();
    }
    public void addAll(List<MateTalkWaitList> items){
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clear(){
        this.items.clear();
        notifyDataSetChanged();
    }

    JoinWaitListViewHolder.OnItemClickListener mListener;
    public void setOnItemClickListener(JoinWaitListViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public JoinWaitListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.view_wait_list, null);
        return new JoinWaitListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JoinWaitListViewHolder holder, int position) {
        holder.setMateTalkWaitList(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
