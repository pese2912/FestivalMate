package com.festival.tacademy.festivalmate.FestivalInfo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.festival.tacademy.festivalmate.Data.Festival;
import com.festival.tacademy.festivalmate.R;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FestivalInfoFragment extends Fragment {


    TextView titleView;
    ViewPager pager;
    FestivalPagerAdapter mAdapter;

    RecyclerView listView;
    FestivalAdapter mAdapter2;

    CirclePageIndicator mIndicator;


    int res;
    public FestivalInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new FestivalPagerAdapter(getChildFragmentManager());
        mIndicator = new CirclePageIndicator(getContext());

        mAdapter2 = new FestivalAdapter();

        mAdapter2.setOnItemClickListener(new FestivalViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, Festival festival) {
                Toast.makeText(getContext(),festival.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_festival_info, container, false);
        titleView = (TextView) getActivity().findViewById(R.id.toolbar_title);
        titleView.setText(getResources().getString(R.string.festival_info));
        pager = (ViewPager)view.findViewById(R.id.pager);
        pager.setAdapter(mAdapter);
        listView = (RecyclerView)view.findViewById(R.id.rv_list);
        listView.setAdapter(mAdapter2);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getContext(), "position : " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mIndicator = (CirclePageIndicator) view.findViewById(R.id.page_indicator);
        mIndicator.setViewPager(pager);



        return  view;
    }

    private void initData() {
        int[] a = new int[] {R.drawable.back1, R.drawable.back2, R.drawable.back3, R.drawable.back4, R.drawable.back5};
        List<Integer> items = new ArrayList<Integer>();
        for(int i=0; i<a.length; i++) {
            items.add(a[i]);
        }
        mAdapter.addAll(items);
        for (int i = 0; i < 10; i++) {

            mAdapter2.add(new Festival("Item: "+i, R.mipmap.ic_launcher, "Date: "+i, "Location: "+i));
        }
    }
}
