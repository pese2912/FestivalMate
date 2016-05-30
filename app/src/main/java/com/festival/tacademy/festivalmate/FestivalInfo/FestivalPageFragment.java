package com.festival.tacademy.festivalmate.FestivalInfo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FestivalPageFragment extends Fragment {


    public FestivalPageFragment() {
        // Required empty public constructor
    }

    public static FestivalPageFragment newInstance(Festival festival) {
        Bundle b = new Bundle();
       // b.putInt("ID", id);
        b.putSerializable("festival",festival);
        FestivalPageFragment f = new FestivalPageFragment();
        f.setArguments(b);
        return f;
    }

    Festival festival;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //festival = getArguments().getInt("ID");
            festival = (Festival)getArguments().get("festival");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_festival_page, container, false);
        ImageView image_festival = (ImageView) view.findViewById(R.id.image_festival);
        TextView name_festival = (TextView)view.findViewById(R.id.text_name);
        //image_festival.setImageResource(res);
        Glide.with(image_festival.getContext()).load(festival.getFestival_img()).into(image_festival);
        name_festival.setText(festival.getFestival_name());
        return view;
    }
}
