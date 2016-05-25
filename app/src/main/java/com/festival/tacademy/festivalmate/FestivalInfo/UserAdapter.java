package com.festival.tacademy.festivalmate.FestivalInfo;

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
 * Created by J.K.Lee on 2016-05-16.
 */
public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {

    List<User> items = new ArrayList<User>();
    Context mContext;

    public UserAdapter(Context context) {
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

    UserViewHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(UserViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user, null);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.setUser(items.get(position));
        holder.setOnItemClickListener(mListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}