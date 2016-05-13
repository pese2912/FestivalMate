package com.festival.tacademy.festivalmate.MateTalk;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.festival.tacademy.festivalmate.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MateTalkFragment extends Fragment {


    public MateTalkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mate_talk, container, false);
    }

}
