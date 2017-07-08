package com.forroom.suhyemin.kimbogyun.songmin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forroom.suhyemin.kimbogyun.songmin.R;

/**
 * Created by ccei on 2016-02-15.
 */
public class ExpandableFragment extends Fragment {

    public ExpandableFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_filter_expandablelist_color1, container, false);
        return v;
    }
}
