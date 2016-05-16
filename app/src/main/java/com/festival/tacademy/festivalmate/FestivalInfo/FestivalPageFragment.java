package com.festival.tacademy.festivalmate.FestivalInfo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.festival.tacademy.festivalmate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FestivalPageFragment extends Fragment {


    public FestivalPageFragment() {
        // Required empty public constructor
    }

    public static FestivalPageFragment newInstance(int id) {
        Bundle b = new Bundle();
        b.putInt("ID", id);
        FestivalPageFragment f = new FestivalPageFragment();
        f.setArguments(b);
        return f;
    }

    int res;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            res = getArguments().getInt("ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_festival_page, container, false);
        ImageView image_festival = (ImageView) view.findViewById(R.id.image_festival);
        image_festival.setImageResource(res);
        return view;
    }
}
