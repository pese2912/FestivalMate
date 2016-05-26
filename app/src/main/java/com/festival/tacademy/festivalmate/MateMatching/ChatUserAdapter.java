package com.festival.tacademy.festivalmate.MateMatching;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.festival.tacademy.festivalmate.Data.User;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-26.
 */
public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserViewHolder> {

    List<User> items = new ArrayList<User>();
    Context mContext;

    public ChatUserAdapter(Context context) {
        mContext = context;
    }

    public void add(User user) {
        items.add(user);
        notifyDataSetChanged();
    }

    public void addAll(List<User> users) {
        items.addAll(users);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    ChatUserViewHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(ChatUserViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public ChatUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user, null);

        return new ChatUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatUserViewHolder holder, int position) {
        holder.setUser(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}