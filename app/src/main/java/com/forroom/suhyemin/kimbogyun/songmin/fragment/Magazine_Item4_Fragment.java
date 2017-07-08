package com.forroom.suhyemin.kimbogyun.songmin.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.forroom.suhyemin.kimbogyun.songmin.ForRoomApplication;
import com.forroom.suhyemin.kimbogyun.songmin.Magazine_info_Activity;
import com.forroom.suhyemin.kimbogyun.songmin.MainActivity;
import com.forroom.suhyemin.kimbogyun.songmin.R;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MagazineValueObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class Magazine_Item4_Fragment extends Fragment {


    public Magazine_Item4_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_magazine__item4_, container, false);

        ImageView res = (ImageView)v.findViewById(R.id.magazineImageItem4);
        MagazineFragment mf = new MagazineFragment();
        final MagazineValueObject mv = mf.magazineVO.get(3);

        Log.i("ssong", mv.mg_title);

        Glide.with(ForRoomApplication.getFRContext()).load(ForRoomConstant.TARGET_URL + mv.magazineImage).placeholder(R.drawable.loading_magazine).into(res);

        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), Magazine_info_Activity.class);
                intent.putExtra("toMagazine_info", mv.mg_id);
                startActivity(intent);
            }
        });
        return v;
    }

}
