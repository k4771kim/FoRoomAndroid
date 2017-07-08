package com.forroom.suhyemin.kimbogyun.songmin.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flipboard.bottomsheet.commons.BottomSheetFragment;
import com.forroom.suhyemin.kimbogyun.songmin.MyRoomWriteActivity;
import com.forroom.suhyemin.kimbogyun.songmin.R;

/**
 * Created by ssongmin on 2016-02-11.
 */
@SuppressLint("ValidFragment")
public class MyRoomWriteBottomFragment extends BottomSheetFragment{
    int i=0;
    private View v = null;
    private ImageView filter_expandable1_1, filter_expandable1_2, filter_expandable1_3, filter_expandable1_4, filter_expandable1_5, filter_expandable1_6;
    private ImageView filter_expandable2_1, filter_expandable2_2, filter_expandable2_3, filter_expandable2_4;
    private ImageView filter_expandable3_1, filter_expandable3_2, filter_expandable3_3, filter_expandable3_4, filter_expandable3_5,filter_expandable3_6 ,filter_expandable3_7,filter_expandable3_8 ,filter_expandable3_9;




    public MyRoomWriteBottomFragment(int j){
        this.i = j;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        switch(i){
            case 0:
                v =inflater.inflate(R.layout.item_filter_expandable_1, container, false);
                filter_expandable1_1 = (ImageView)v.findViewById(R.id.filter_expandable1_1);
                filter_expandable1_2 = (ImageView)v.findViewById(R.id.filter_expandable1_2);
                filter_expandable1_3 = (ImageView)v.findViewById(R.id.filter_expandable1_3);
                filter_expandable1_4 = (ImageView)v.findViewById(R.id.filter_expandable1_4);
                filter_expandable1_5 = (ImageView)v.findViewById(R.id.filter_expandable1_5);
                filter_expandable1_6 = (ImageView)v.findViewById(R.id.filter_expandable1_6);


                filter_expandable1_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i1_1){
                            MyRoomWriteActivity.myroomwritetag.add("10평미만");
                            init();
                            MyRoomWriteActivity.i1_1=false;
                       }else{
                            MyRoomWriteActivity.myroomwritetag.remove("10평미만");
                            init2("#10평미만");
                            MyRoomWriteActivity.i1_1 = true;
                        }
                    }
                });
                filter_expandable1_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i1_2){
                            MyRoomWriteActivity.myroomwritetag.add("10평대");
                            init();
                            MyRoomWriteActivity.i1_2=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("10평대");
                            init2("#10평대");
                            MyRoomWriteActivity.i1_2 = true;
                        }
                    }
                });
                filter_expandable1_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i1_3){
                            MyRoomWriteActivity.myroomwritetag.add("20평대");
                            init();
                            MyRoomWriteActivity.i1_3=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("20평대");
                            init2("#20평대");
                            MyRoomWriteActivity.i1_3 = true;
                        }
                    }
                });
                filter_expandable1_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i1_4){
                            MyRoomWriteActivity.myroomwritetag.add("30평대");
                            init();
                            MyRoomWriteActivity.i1_4=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("30평대");
                            init2("30평대");
                            MyRoomWriteActivity.i1_4 = true;
                        }
                    }
                });
                filter_expandable1_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i1_5){
                            MyRoomWriteActivity.myroomwritetag.add("40평대");
                            init();
                            MyRoomWriteActivity.i1_5=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("40평대");
                            init2("40평대");
                            MyRoomWriteActivity.i1_5 = true;
                        }
                    }
                });
                filter_expandable1_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i1_6){
                            MyRoomWriteActivity.myroomwritetag.add("50평이상");
                            init();
                            MyRoomWriteActivity.i1_6=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("50평이상");
                            init2("#50평이상");
                            MyRoomWriteActivity.i1_6 = true;
                        }
                    }
                });

                break;
            case 1:
                v= inflater.inflate(R.layout.item_filter_expandable_2, container, false);
                filter_expandable2_1 = (ImageView)v.findViewById(R.id.filter_expandable2_1);
                filter_expandable2_2 = (ImageView)v.findViewById(R.id.filter_expandable2_2);
                filter_expandable2_3 = (ImageView)v.findViewById(R.id.filter_expandable2_3);
                filter_expandable2_4 = (ImageView)v.findViewById(R.id.filter_expandable2_4);

                filter_expandable2_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i2_1){
                            MyRoomWriteActivity.myroomwritetag.add("원룸");
                            init();
                            MyRoomWriteActivity.i2_1=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("원룸");
                            init2("#원룸");
                            MyRoomWriteActivity.i2_1 = true;
                        }
                    }
                });
                filter_expandable2_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i2_2){
                            MyRoomWriteActivity.myroomwritetag.add("빌라");
                            init();
                            MyRoomWriteActivity.i2_2=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("빌라");
                            init2("#빌라");
                            MyRoomWriteActivity.i2_2 = true;
                        }
                    }
                });
                filter_expandable2_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i2_3){
                            MyRoomWriteActivity.myroomwritetag.add("주택");
                            init();
                            MyRoomWriteActivity.i2_3=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("주택");
                            init2("#주택");
                            MyRoomWriteActivity.i2_3 = true;
                        }
                    }
                });
                filter_expandable2_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i2_4){
                            MyRoomWriteActivity.myroomwritetag.add("아파트");
                            init();
                            MyRoomWriteActivity.i2_4=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("아파트");
                            init2("#아파트");
                            MyRoomWriteActivity.i2_4 = true;
                        }
                    }
                });

                break;
            case 2:
                v=inflater.inflate(R.layout.item_filter_expandable_3, container, false);
                filter_expandable3_1 = (ImageView)v.findViewById(R.id.filter_expandable3_1);
                filter_expandable3_2 = (ImageView)v.findViewById(R.id.filter_expandable3_2);
                filter_expandable3_3 = (ImageView)v.findViewById(R.id.filter_expandable3_3);
                filter_expandable3_4 = (ImageView)v.findViewById(R.id.filter_expandable3_4);
                filter_expandable3_5 = (ImageView)v.findViewById(R.id.filter_expandable3_5);
                filter_expandable3_6 = (ImageView)v.findViewById(R.id.filter_expandable3_6);
                filter_expandable3_7 = (ImageView)v.findViewById(R.id.filter_expandable3_7);
                filter_expandable3_8 = (ImageView)v.findViewById(R.id.filter_expandable3_8);
                filter_expandable3_9 = (ImageView)v.findViewById(R.id.filter_expandable3_9);

                filter_expandable3_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(MyRoomWriteActivity.i3_1){
                            MyRoomWriteActivity.myroomwritetag.add("침실");
                            init();
                            MyRoomWriteActivity.i3_1=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("침실");
                            init2("#침실");
                            MyRoomWriteActivity.i3_1=true;
                        }
                    }
                });

                filter_expandable3_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(MyRoomWriteActivity.i3_2){
                            MyRoomWriteActivity.myroomwritetag.add("거실");
                            init();
                            MyRoomWriteActivity.i3_2=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("거실");
                            init2("#거실");
                            MyRoomWriteActivity.i3_2 = true;
                        }
                    }
                });
                filter_expandable3_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i3_3){
                            MyRoomWriteActivity.myroomwritetag.add("욕실");
                            init();
                            MyRoomWriteActivity.i3_3=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("욕실");
                            init2("#욕실");
                            MyRoomWriteActivity.i3_3 = true;
                        }
                    }
                });
                filter_expandable3_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i3_4){
                            MyRoomWriteActivity.myroomwritetag.add("주방");
                            init();
                            MyRoomWriteActivity.i3_4=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("주방");
                            init2("#주방");
                            MyRoomWriteActivity.i3_4 = true;
                        }
                    }
                });
                filter_expandable3_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i3_5){
                            MyRoomWriteActivity.myroomwritetag.add("서재");
                            init();
                            MyRoomWriteActivity.i3_5=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("서재");
                            init2("#서재");
                            MyRoomWriteActivity.i3_5 = true;
                        }
                    }
                });
                filter_expandable3_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i3_6){
                            MyRoomWriteActivity.myroomwritetag.add("드레스룸");
                            init();
                            MyRoomWriteActivity.i3_6=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("드레스룸");
                            init2("#드레스룸");
                            MyRoomWriteActivity.i3_6 = true;
                        }
                    }
                });
                filter_expandable3_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(MyRoomWriteActivity.i3_7){
                            MyRoomWriteActivity.myroomwritetag.add("아이방");
                            init();
                            MyRoomWriteActivity.i3_7=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("아이방");
                            init2("#아이방");
                            MyRoomWriteActivity.i3_7 = true;
                        }
                    }
                });
                filter_expandable3_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i3_8){
                            MyRoomWriteActivity.myroomwritetag.add("현관/발코니");
                            init();
                            MyRoomWriteActivity.i3_8=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("현관/발코니");
                            init2("#현관/발코니");
                            MyRoomWriteActivity.i3_8 = true;
                        }
                    }
                });
                filter_expandable3_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(MyRoomWriteActivity.i3_9){
                            MyRoomWriteActivity.myroomwritetag.add("야외");
                            init();
                            MyRoomWriteActivity.i3_9=false;
                        }else{
                            MyRoomWriteActivity.myroomwritetag.remove("야외");
                            init2("#야외");
                            MyRoomWriteActivity.i3_9 = true;
                        }
                    }
                });

                break;
        }
        return v;
    }
    private void init(){
//        Log.e("ssong test", MyRoomWriteActivity.myroomwritetag + "");
//        filter_text_iv.setVisibility(View.INVISIBLE);
        for(int i=0; i<MyRoomWriteActivity.myroomwritetag.size(); i++){
            if(i ==0){
                MyRoomWriteActivity.mr_write_tag1.setText("#"+MyRoomWriteActivity.myroomwritetag.get(i));
            }else if(i ==1){
                MyRoomWriteActivity.mr_write_tag2.setText("#"+MyRoomWriteActivity.myroomwritetag.get(i));
            }else if(i ==2){
                MyRoomWriteActivity.mr_write_tag3.setText("#"+MyRoomWriteActivity.myroomwritetag.get(i));
            }else if(i ==3){
                MyRoomWriteActivity.mr_write_tag4.setText("#"+MyRoomWriteActivity.myroomwritetag.get(i));
            }else if(i ==4){
                MyRoomWriteActivity.mr_write_tag5.setText("#"+MyRoomWriteActivity.myroomwritetag.get(i));
            }

        }
    }
    private void init2(String name){
//        Log.e("ssong test", MyRoomWriteActivity.myroomwritetag+"");

        init();
        for(int j =MyRoomWriteActivity.myroomwritetag.size(); j<5; j++){
            if(j ==0){
                MyRoomWriteActivity.mr_write_tag1.setText("");
            }else if(j ==1){
                MyRoomWriteActivity.mr_write_tag2.setText("");
            }else if(j ==2){
                MyRoomWriteActivity.mr_write_tag3.setText("");
            }else if(j ==3){
                MyRoomWriteActivity.mr_write_tag4.setText("");
            }else if(j ==4){
                MyRoomWriteActivity.mr_write_tag5.setText("");
            }
        }
    }
}
