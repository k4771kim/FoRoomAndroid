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
import com.forroom.suhyemin.kimbogyun.songmin.R;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MagazineValueObject;

/**
 * Created by ccei on 2016-01-26.
 */
public class Magazine_Item_Fragment extends Fragment {
    ImageView res;


    public Magazine_Item_Fragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_magazine_item, container, false);

        res = (ImageView)v.findViewById(R.id.magazineImageItem);
        MagazineFragment mf = new MagazineFragment();
        final MagazineValueObject mv = mf.magazineVO.get(0);

        Log.i("ssong", mv.mg_title);
        Log.i("ssong", mv.magazineImage);

        Glide.with(ForRoomApplication.getFRContext()).load(ForRoomConstant.TARGET_URL + mv.magazineImage).placeholder(R.drawable.loading_magazine).into(res);

        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), Magazine_info_Activity.class);
                intent.putExtra("toMagazine_info",mv.mg_id);
                startActivity(intent);
            }
        });
        return v;
    }

}