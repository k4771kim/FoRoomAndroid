package com.forroom.suhyemin.kimbogyun.songmin.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.forroom.suhyemin.kimbogyun.songmin.ForRoomApplication;
import com.forroom.suhyemin.kimbogyun.songmin.Magazine_info_Activity;
import com.forroom.suhyemin.kimbogyun.songmin.R;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;

/**
 * A simple {@link Fragment} subclass.
 */
public class Magazine_info_item1_Fragment extends Fragment {


    public Magazine_info_item1_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_magazine_info_item1_, container, false);
        ImageView  res =(ImageView)v.findViewById(R.id.magazine_info_image1);
        TextView text = (TextView)v.findViewById(R.id.magazine_info_text1);
        TextView title = (TextView)v.findViewById(R.id.magazine_info_title);
        TextView date = (TextView)v.findViewById(R.id.magazine_info_date);

        Glide.with(ForRoomApplication.getFRContext()).load(ForRoomConstant.TARGET_URL +
                Magazine_info_Activity.MZIVO.mgz_info_pic[0]).placeholder(R.drawable.loading_mgz_info).into(res);
        text.setText(Magazine_info_Activity.MZIVO.mgz_info_text[0]);
//        Log.e("ssong text", Magazine_info_Activity.MZIVO.mgz_info_title);
        title.setText(Magazine_info_Activity.MZIVO.mgz_info_title);
        date.setText(Magazine_info_Activity.MZIVO.mgz_info_date);

        return v;
    }

}
