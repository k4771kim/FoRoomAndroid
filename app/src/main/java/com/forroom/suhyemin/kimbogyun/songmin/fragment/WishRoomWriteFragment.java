package com.forroom.suhyemin.kimbogyun.songmin.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.forroom.suhyemin.kimbogyun.songmin.ForRoomApplication;
import com.forroom.suhyemin.kimbogyun.songmin.GoodsItemListActivity;
import com.forroom.suhyemin.kimbogyun.songmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WishRoomWriteFragment extends Fragment {
    ImageView image1_1, image1_2, image1_3, image1_4;
    ImageView image2_1, image2_2, image2_3, image2_4;
    ImageView image3_1, image3_2, image3_3, image3_4;
    ImageView image4_1, image4_2, image4_3, image4_4;

    public WishRoomWriteFragment() {
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wish_room_write, container, false);

        image1_1 = (ImageView)v.findViewById(R.id.wr_write_sofa_btn);
        image1_2 = (ImageView)v.findViewById(R.id.wr_write_chair_btn);
        image1_3 = (ImageView)v.findViewById(R.id.wr_write_desk_btn);
        image1_4 = (ImageView)v.findViewById(R.id.wr_write_kitchen_btn);
        image2_1 = (ImageView)v.findViewById(R.id.wr_write_sofatable_btn);
        image2_2 = (ImageView)v.findViewById(R.id.wr_write_dressingtable_btn);
        image2_3 = (ImageView)v.findViewById(R.id.wr_write_bed_btn);
        image2_4 = (ImageView)v.findViewById(R.id.wr_write_closet_btn);
        image3_1 = (ImageView)v.findViewById(R.id.wr_write_lamp_btn);
        image3_2 = (ImageView)v.findViewById(R.id.wr_write_shelf_btn);
        image3_3 = (ImageView)v.findViewById(R.id.wr_write_curtain_btn);
        image3_4 = (ImageView)v.findViewById(R.id.wr_write_deco_btn);
        image4_1 = (ImageView)v.findViewById(R.id.wr_write_color);
        image4_2 = (ImageView)v.findViewById(R.id.wr_write_pattern);
        image4_3 = (ImageView)v.findViewById(R.id.wr_write_style);
        image4_4 = (ImageView)v.findViewById(R.id.wr_write_etc);

        image1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList",11);
                startActivity(intent);
            }
        });
        image1_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList",12);
                startActivity(intent);
            }
        });
        image1_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList",13);
                startActivity(intent);
            }
        });
        image1_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList",14);
                startActivity(intent);
            }
        });
        image2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList",21);
                startActivity(intent);
            }
        });
        image2_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList",22);
                startActivity(intent);
            }
        });
        image2_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList",23);
                startActivity(intent);
            }
        });
        image2_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList",24);
                startActivity(intent);
            }
        });
        image3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList",31);
                startActivity(intent);
            }
        });
        image3_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList",32);
                startActivity(intent);
            }
        });
        image3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList",33);
                startActivity(intent);
            }
        });
        image3_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList",34);
                startActivity(intent);
            }
        });
        image4_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList", 41);
                startActivity(intent);
            }
        });
        image4_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList", 42);
                startActivity(intent);
            }
        });
        image4_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList", 43);
                startActivity(intent);
            }
        });
        image4_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemListActivity.class);
                intent.putExtra("toGoodsItemList", 44);
                startActivity(intent);
            }
        });

        return v;
    }

}
