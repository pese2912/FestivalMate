package com.festival.tacademy.festivalmate.FestivalInfo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.R;

/**
 * Created by J.K.Lee on 2016-05-16.
 */
public class FestivalDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_FESTIVAL_PHOTO = 1;
    public static final int VIEW_TYPE_FESTIVAL_LINEUP = 2;
    public static final int VIEW_TYPE_LETSGO = 3;
    public static final int VIEW_TYPE_MAPVIEW = 4;
    public static final int VIEW_TYPE_TITLE = 5;
    public static final int VIEW_TICKET_LINK = 6;

    Festival festival;

    public void setFestval(Festival festival) {
        this.festival = festival;
        notifyDataSetChanged();
    }


    UserViewHolder.OnItemClickListener mListener;

    public void setOnItemClickListener(UserViewHolder.OnItemClickListener listener) {
        mListener = listener;
    }

    MapViewHolder.OnItemClickListner mListener3;

    public void setOnItemClickListener(MapViewHolder.OnItemClickListner listener) {
        mListener3 = listener;
    }

    TicketLinkHolder.OnItemClickListner mListener2;

    public void setOnItemClickListener2(TicketLinkHolder.OnItemClickListner listener) {
        mListener2 = listener;
    }

//    MapViewHolder.OnItemClickListener mListener3;
//
//    public void setOnItemClickListener3(MapViewHolder.OnItemClickListener listener) {
//        mListener3 = listener;
//    }

    @Override
    public int getItemViewType(int position) {

        if( position == 0 ) {
            return VIEW_TYPE_FESTIVAL_PHOTO;
        }
        position--;
        if( position == 0 ) {
            return VIEW_TICKET_LINK;
        }
        position--;
        if ( festival.getLineups().size() > 0 ) {
            if( position == 0 ) {
                return VIEW_TYPE_TITLE;
            }
            position--;
            if( position < festival.getLineups().size() ) {
                return VIEW_TYPE_FESTIVAL_LINEUP;
            }
            position -= festival.getLineups().size();
        }

        if( position == 0 ) {
            return VIEW_TYPE_TITLE;
        }
        position--;
        if( position == 0 ) {
            return VIEW_TYPE_LETSGO;
        }
        position--;
        if( position == 0 ) {
            return VIEW_TYPE_TITLE;
        }
        position--;
        if( position == 0 ) {
            return VIEW_TYPE_MAPVIEW;
        }
        position--;
        throw new IllegalArgumentException("Invalid Position");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_FESTIVAL_PHOTO: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_festival_photo, parent, false);
                return new PhotoViewHolder(view);
            }
            case VIEW_TICKET_LINK: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_ticket_link, null);
                return new TicketLinkHolder(view);
            }
            case VIEW_TYPE_TITLE: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_title, null);
                return new TitleViewHolder(view);
            }
            case VIEW_TYPE_FESTIVAL_LINEUP: {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_lineup, null);
                return new LineupViewHolder(view);
            }
            case VIEW_TYPE_LETSGO: {
                View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.view_letsgo, parent, false);
                return new LetsgoViewHolder(view);
            }

            case VIEW_TYPE_MAPVIEW: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_map, null);
                return new MapViewHolder(view);
            }
        }
        throw new IllegalArgumentException("Invalid position");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if( position == 0 ) {
            PhotoViewHolder h = (PhotoViewHolder)holder;
            h.setPhoto(festival);
            return;
        }
        position--;
        if( position == 0 ) {
            TicketLinkHolder h = (TicketLinkHolder)holder;
            h.setText(festival);
            h.setOnItemClickListener(mListener2);
            return;
        }
        position--;
        if( festival.getLineups().size() > 0 ) {
            if( position == 0 ) {
                TitleViewHolder h = (TitleViewHolder)holder;
                h.setData("Line up");
                return;
            }
            position--;
            if( position < festival.getLineups().size() ) {
                LineupViewHolder h = (LineupViewHolder) holder;
                h.setText(festival.getLineups().get(position));
                return;
            }
            position -= festival.getLineups().size();
        }

        if( position == 0 ) {
            TitleViewHolder h = (TitleViewHolder)holder;
            h.setData("갈꺼야");
            return;
        }
        position--;
        if( position == 0 ) {
            LetsgoViewHolder h = (LetsgoViewHolder)holder;
            h.setList(festival.getFestival_going_mem(), mListener);
            return;
        }
        position--;
        if( position == 0 ) {
            TitleViewHolder h = (TitleViewHolder)holder;
            h.setData("장소");
            return;
        }
        position--;

        if( position == 0 ) {
            MapViewHolder h = (MapViewHolder)holder;
            h.setLocation(festival);
            h.setOnItemClickListener(mListener3);
            return;
        }

        throw new IllegalArgumentException("Invalid Position");
    }

    @Override
    public int getItemCount() {
        if( festival == null ) {
            return 0;
        }
        int size = 2;
        if( festival.getLineups().size() > 0 ) {
            size++;
            size += festival.getLineups().size();
        }
        size+=4;

        return size;
    }
}