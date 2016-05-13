package com.festival.tacademy.festivalmate.MateMatching;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.festival.tacademy.festivalmate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MateMatchingFragment extends Fragment {


    TextView titleView;

    public MateMatchingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mate_matching, container, false);

        titleView = (TextView) getActivity().findViewById(R.id.toolbar_title);
        titleView.setText(getResources().getString(R.string.mate_matching));

        return view;
    }

}
