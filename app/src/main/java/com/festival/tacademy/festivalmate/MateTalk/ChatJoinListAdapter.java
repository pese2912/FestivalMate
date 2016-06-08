package com.festival.tacademy.festivalmate.MateTalk;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.Artist;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.Data.MateTalkWaitJoinList;
import com.festival.tacademy.festivalmate.Data.MateTalkWaitList;
import com.festival.tacademy.festivalmate.FestivalInfo.FestivalViewHolder;
import com.festival.tacademy.festivalmate.FestivalInfo.LetsgoViewHolder;
import com.festival.tacademy.festivalmate.FestivalInfo.LineupViewHolder;
import com.festival.tacademy.festivalmate.FestivalInfo.PhotoViewHolder;
import com.festival.tacademy.festivalmate.FestivalInfo.TitleViewHolder;
import com.festival.tacademy.festivalmate.MyApplication;
import com.festival.tacademy.festivalmate.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-05-19.
 */
public class ChatJoinListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_HEADER_WAIT = 1;
    public static final int VIEW_TYPE_HEADER_JOIN  = 2;
    public static final int VIEW_TYPE_APPROVE_WAITER = 3;
    public static final int VIEW_TYPE_CHAT_JOINER = 4;
    public static final int VIEW_TYPE_FESTIVAL_INFO = 5;
    public static final int VIEW_TYPE_PREFER_ARTIST = 6;

    MateTalkWaitJoinList list;

    Festival festival;
    List<Artist> artists = new ArrayList<>();


    public void setMateTalkWaitJoinList(MateTalkWaitJoinList list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0) {
            return VIEW_TYPE_FESTIVAL_INFO;
        }
        position--;
        if(position == 0) {
            return VIEW_TYPE_PREFER_ARTIST;
        }
        position--;
        if( position == 0 ) {
            return VIEW_TYPE_HEADER_WAIT;
        }
        position--;

        if (list.getChatroom_waitings().size() > 0) {
            if (position < list.getChatroom_waitings().size()) {
                return VIEW_TYPE_APPROVE_WAITER;
            }
            position -= list.getChatroom_waitings().size();
        }

        if (position == 0) {
            return VIEW_TYPE_HEADER_JOIN;
        }
        position--;


        if (list.getChatroom_members().size() > 0) {
            if (position < list.getChatroom_members().size()) {
                return VIEW_TYPE_CHAT_JOINER;
            }
            position -= list.getChatroom_members().size();
        }


        throw new IllegalArgumentException("Invalid Position");

    }

    ApproveWaiterViewHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(ApproveWaiterViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    ChatJoinerViewHolder.OnItemClickListener mListener3;

    public void setOnItemClickListener(ChatJoinerViewHolder.OnItemClickListener listener) {
        mListener3 = listener;
    }

    ApproveWaiterViewHolder.OnItemClickListener2 mListener2;

    public void setOnItemClickListener2(ApproveWaiterViewHolder.OnItemClickListener2 listener) {
        mListener2 = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_FESTIVAL_INFO: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_chatroom_festival_info, null);
                return new ChatFestivalInfoViewHolder(view);
            }
            case VIEW_TYPE_PREFER_ARTIST: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_chatroom_prefer_artist, null);
                return new ChatPreferArtistViewHolder(view);
            }
            case VIEW_TYPE_HEADER_WAIT: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_header_wait, null);
                return new HeaderWaitViewHolder(view);
            }

            case VIEW_TYPE_HEADER_JOIN: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_header_join, null);
                return new HeaderJoinViewHolder(view);
            }
            case VIEW_TYPE_APPROVE_WAITER: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_waiter_list, null);
                return new ApproveWaiterViewHolder(view);
            }

            case VIEW_TYPE_CHAT_JOINER: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_joiner_list, null);
                return new ChatJoinerViewHolder(view);
            }
        }

        throw new IllegalArgumentException("Invalid position");
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( position == 0 ) {
            ChatFestivalInfoViewHolder h = (ChatFestivalInfoViewHolder)holder;
            h.setText(list.getFestival_name(), list.getFestival_lineups(), list.getFestival_location());
            return;
        }

        position--;
        if( position == 0 ) {
            ChatPreferArtistViewHolder h = (ChatPreferArtistViewHolder)holder;
            h.setData(list.getChatroom_lineups());
            return;
        }

        position--;
        if( position == 0 ) {
            HeaderWaitViewHolder h = (HeaderWaitViewHolder)holder;
            return;
        }


        position--;
        if ( list.getChatroom_waitings().size() > 0 ) {
            if( position < list.getChatroom_waitings().size() ) {

                ApproveWaiterViewHolder h = (ApproveWaiterViewHolder)holder;
                h.setApproveWaiter(list.getChatroom_waitings().get(position),list.getChatroom_no(),list.getIs_host());
                h.setOnItemClickListener(mListener);
                h.setOnItemClickListener2(mListener2);
                return;

            }
            position -= list.getChatroom_waitings().size();
        }

        if( position == 0 ) {
            HeaderJoinViewHolder h = (HeaderJoinViewHolder)holder;
            return;
        }

        position--;
        if(list.getChatroom_members().size() > 0) {
            if (position < list.getChatroom_members().size()) {
                ChatJoinerViewHolder h = (ChatJoinerViewHolder)holder;
                h.setChatJoin(list.getChatroom_members().get(position), list.getChatroom_no(), list.getIs_host());
                h.setOnItemClickListener(mListener3);
                return;
            }
            position -= list.getChatroom_members().size();
        }

        throw new IllegalArgumentException("Invalid Position");

    }

    @Override
    public int getItemCount() {
        int size = 0;

        if( list == null ) {
            return size;
        }

        size+=2;
        size++;
        if (list.getChatroom_waitings().size() > 0) {
            size += list.getChatroom_waitings().size();
        }

        size+=1;
        if (list.getChatroom_members().size() > 0) {
            size += list.getChatroom_members().size();
        }

        //Toast.makeText(MyApplication.getContext(),""+size,Toast.LENGTH_SHORT).show();

        return size;
    }

}
