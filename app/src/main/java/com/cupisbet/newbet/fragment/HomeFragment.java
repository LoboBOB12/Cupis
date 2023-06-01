package com.cupisbet.newbet.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cupisbet.newbet.MyPagerAdapter;
import com.cupisbet.newbet.R;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager2;
    private WormDotsIndicator dotsIndicator;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager2 = view.findViewById(R.id.view_pager);
        dotsIndicator = view.findViewById(R.id.dots_indicator);

        // Create a list of layouts for the ViewPager2
        List<Integer> layoutIds = new ArrayList<>();
        layoutIds.add(R.layout.layout_1);
        layoutIds.add(R.layout.layout_2);

        // Create and set the adapter for the ViewPager2
        MyPagerAdapter adapter = new MyPagerAdapter(layoutIds);
        viewPager2.setAdapter(adapter);

        // Set up the indicator for the ViewPager2
        dotsIndicator.setViewPager2(viewPager2);

        return view;
    }
}
