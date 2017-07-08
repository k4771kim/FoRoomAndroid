package com.forroom.suhyemin.kimbogyun.songmin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;

import java.util.ArrayList;

public class ForRoomFilter extends AppCompatActivity {

    private ExpandableListView expandableListView1;
    private int typefilter;
    private String type = null;
    private TextView filter_hash1, filter_hash2, filter_hash3, filter_hash4, filter_hash5, filter_text_iv;
    private ArrayList<String> filterTag;
    private String[] intentarray;
    private ImageView left_icon;
    private String a = "", b = "", c = "";

    private ImageView filter_expandable1_1, filter_expandable1_2, filter_expandable1_3, filter_expandable1_4, filter_expandable1_5, filter_expandable1_6;
    private ImageView filter_expandable2_1, filter_expandable2_2, filter_expandable2_3, filter_expandable2_4;
    private ImageView filter_expandable3_1, filter_expandable3_2, filter_expandable3_3, filter_expandable3_4, filter_expandable3_5, filter_expandable3_6, filter_expandable3_7, filter_expandable3_8, filter_expandable3_9;

    private ImageView[] colorTagImageView = new ImageView[12];
    private ImageView[] floorColorTagImageView = new ImageView[12];

    public final int[] unselectedTagImageViewResource = { R.drawable.btn_t_beige_04 ,R.drawable.btn_t_wood_01, R.drawable.btn_t_black_08, R.drawable.btn_t_white_02, R.drawable.btn_t_grey_03, R.drawable.btn_t_green_05,
            R.drawable.btn_t_violet_06, R.drawable.btn_t_red_07, R.drawable.btn_t_blue_09, R.drawable.btn_t_gold_10,
            R.drawable.btn_t_brown_11, R.drawable.btn_t_pink_12};

    public final int[] selectedTagImageViewResource = { R.drawable.btn_t_beige_04_s  ,R.drawable.btn_t_wood_01_s, R.drawable.btn_t_black_08_s, R.drawable.btn_t_white_02_s, R.drawable.btn_t_grey_03_s, R.drawable.btn_t_green_05_s,
            R.drawable.btn_t_violet_06_s, R.drawable.btn_t_red_07_s, R.drawable.btn_t_blue_09_s, R.drawable.btn_t_gold_10_s,
            R.drawable.btn_t_brown_11_s, R.drawable.btn_t_pink_12_s};

    public final int[] unselectedFloorTagImageViewResource = {R.drawable.btn_h_wood_01, R.drawable.btn_h_white_02, R.drawable.btn_h_grey_03, R.drawable.btn_h_beige_04, R.drawable.btn_h_green_05,
            R.drawable.btn_h_violet_06, R.drawable.btn_h_red_07, R.drawable.btn_h_black_08, R.drawable.btn_h_blue_09, R.drawable.btn_h_gold_10,
            R.drawable.btn_h_brown_11, R.drawable.btn_h_pink_12};
    public final int[] selectedFloorTagImageViewResource = {R.drawable.btn_h_wood_01_s, R.drawable.btn_h_white_02_s, R.drawable.btn_h_grey_03_s, R.drawable.btn_h_beige_04_s, R.drawable.btn_h_green_05_s,
            R.drawable.btn_h_violet_06_s, R.drawable.btn_h_red_07_s, R.drawable.btn_h_black_08_s, R.drawable.btn_h_blue_09_s, R.drawable.btn_h_gold_10_s,
            R.drawable.btn_h_brown_11_s, R.drawable.btn_h_pink_12_s};

    public final int[] floorTagImageViewId = {R.id.imageView51, R.id.imageView52, R.id.imageView53, R.id.imageView55,
            R.id.imageView56, R.id.imageView54, R.id.imageView62, R.id.imageView57, R.id.imageView58, R.id.imageView59, R.id.imageView60, R.id.imageView61};

    public final int[] tagImageViewId = {R.id.imageView38, R.id.imageView39, R.id.imageView41, R.id.imageView42, R.id.imageView43, R.id.imageView44,
            R.id.imageView45, R.id.imageView46, R.id.imageView47, R.id.imageView48, R.id.imageView49, R.id.imageView50};


    private Boolean i1_1 = true, i1_2 = true, i1_3 = true, i1_4 = true, i1_5 = true, i1_6 = true;
    private Boolean i2_1 = true, i2_2 = true, i2_3 = true, i2_4 = true;
    private Boolean i3_1 = true, i3_2 = true, i3_3 = true, i3_4 = true, i3_5 = true, i3_6 = true, i3_7 = true, i3_8 = true, i3_9 = true;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e("ssong", ""+requestCode);
        if(resultCode==ForRoomConstant.WISHROOM_WRITE_FILTER_FINISH_RESULT_CODE){
            Intent intent = new Intent();
            setResult(ForRoomConstant.FILTER_TO_MAIN_FINISH_RESULT_CODE, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_room_filter);


        intentarray = new String[5];
        Intent intent = getIntent();
        type = intent.getStringExtra("toFilter");
        typefilter = intent.getIntExtra("toFilter2", 0);
//        Log.e("메인에서 넘어온 값", typefilter + "");

        left_icon = (ImageView) findViewById(R.id.filter_left_back);
        left_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView ok = (ImageView) findViewById(R.id.filter_ok_icon);
        filter_text_iv = (TextView) findViewById(R.id.filter_text_invisible);
        if (type.equalsIgnoreCase("fromMainActivity")) {
            if (typefilter == 1) {
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intentarray[0] = filter_hash1.getText().toString();
                        intentarray[1] = filter_hash2.getText().toString();
                        intentarray[2] = filter_hash3.getText().toString();
                        MainActivity.tagarray.add(a);
                        MainActivity.tagarray.add(b);
                        MainActivity.tagarray.add(c);

                        intent.putExtra("toMain", intentarray);
                        setResult(ForRoomConstant.FILTER_MYROOM_RESULT_CODE, intent);
                        finish();
//                        Toast.makeText(ForRoomApplication.getFRContext(), " 이제 안드로이드가 할꺼임", Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (typefilter == 2) {
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intentarray[0] = filter_hash1.getText().toString();
                        intentarray[1] = filter_hash2.getText().toString();
                        intentarray[2] = filter_hash3.getText().toString();
//                        MainActivity.tagarray.add(filter_hash1.getText().toString());
//                        MainActivity.tagarray.add(filter_hash2.getText().toString());
//                        MainActivity.tagarray.add(filter_hash3.getText().toString());

                        MainActivity.tagarray.add(a);
                        MainActivity.tagarray.add(b);
                        MainActivity.tagarray.add(c);
//                        Log.e("tag1", a);
//                        Log.e("tag2", b);
//                        Log.e("tag3", c);

                        intent.putExtra("toMain", intentarray);
                        setResult(ForRoomConstant.FILTER_WISHROOM_RESYLT_CODE, intent);
                        finish();
//                      Toast.makeText(ForRoomApplication.getFRContext(), " 이제 안드로이드가 할꺼임", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (type.equalsIgnoreCase("fromWishRoom")) {
            filter_text_iv.setText("원하는 컨셉을 설정하세요");
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ForRoomFilter.this, WishRoomWriteActivity.class);
                    String[] intentarray = new String[5];

//                    for(int i=0; i<filterTag.size(); i++){
//                        intentarray[i] = filterTag.get(i);
//                    }

                    intentarray[0] = a;
                    intentarray[1] = b;
                    intentarray[2] = c;
                    intent.putExtra("toWishRoomWrite", intentarray);
                    startActivityForResult(intent, 24);
                }
            });
        } else {
//            Log.e("ssong intent error", "인텐트에서 값이 안넘어옴");
        }
        expandableListView1 = (ExpandableListView) findViewById(R.id.filter_expandableListView1);
        filterExpandableAdapter ad = new filterExpandableAdapter(ForRoomFilter.this);
        expandableListView1.setAdapter(ad);


        for(int i = 0 ; i < ad.getGroupCount();i++) expandableListView1.expandGroup(i);

        filter_hash1 = (TextView) findViewById(R.id.filter_hash1);
        filter_hash2 = (TextView) findViewById(R.id.filter_hash2);
        filter_hash3 = (TextView) findViewById(R.id.filter_hash3);
        filter_hash4 = (TextView) findViewById(R.id.filter_hash4);
        filter_hash5 = (TextView) findViewById(R.id.filter_hash5);

        filterTag = new ArrayList<>();
    }
    private class filterExpandableAdapter extends BaseExpandableListAdapter {
        Context context;

        public filterExpandableAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getGroupCount() {
            if (type.equalsIgnoreCase("fromWishRoom")) {
                return 4;
            }else if(typefilter==1){
                return 4;
            }
            return 7;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (groupPosition == 0 || groupPosition == 4) {
                return 0;
            }
            return 1;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return true;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return true;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View v = null;
            TextView text = null;
            ImageView image = null;
            if (v == null) {
                if (groupPosition == 0 || groupPosition == 4) {
                    v = LayoutInflater.from(context).inflate(R.layout.item_filter_expandablelist_header, null);
                    text = (TextView) v.findViewById(R.id.filter_header);
                    image = (ImageView) v.findViewById(R.id.filter_header_image);
                } else {
                    v = LayoutInflater.from(context).inflate(R.layout.item_filter_expandable_group, null);
                    text = (TextView) v.findViewById(R.id.filter_expandable_group_title);
                }
            }
//            else{
//                if(groupPosition ==0 || groupPosition==4){
//                    text = (TextView)v.findViewById(R.id.filter_header);
//                    image = (ImageView)v.findViewById(R.id.filter_header_image);
//                }else{
//                    text =(TextView)v.findViewById(R.id.filter_expandable_group_title);
//                }
//            }
            if (groupPosition == 0) {
                text.setText("House");
                image.setImageResource(R.drawable.icon_house);
            } else if (groupPosition == 1) {
                text.setText("평수");
            } else if (groupPosition == 2) {
                text.setText("유형");
            } else if (groupPosition == 3) {
                text.setText("공간");
            } else if (groupPosition == 4) {
                text.setText("Color Tag");
                image.setImageResource(R.drawable.icon_tag);
            } else if (groupPosition == 5) {
                text.setText("제품");
            } else if (groupPosition == 6) {
                text.setText("벽/천장/바닥");
            }

            return v;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View v = null;
            if (groupPosition == 1) {
                v = LayoutInflater.from(context).inflate(R.layout.item_filter_expandable_1, null);
                filter_expandable1_1 = (ImageView) v.findViewById(R.id.filter_expandable1_1);
                filter_expandable1_2 = (ImageView) v.findViewById(R.id.filter_expandable1_2);
                filter_expandable1_3 = (ImageView) v.findViewById(R.id.filter_expandable1_3);
                filter_expandable1_4 = (ImageView) v.findViewById(R.id.filter_expandable1_4);
                filter_expandable1_5 = (ImageView) v.findViewById(R.id.filter_expandable1_5);
                filter_expandable1_6 = (ImageView) v.findViewById(R.id.filter_expandable1_6);





                filter_expandable1_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i1_1) {
                            filterTag.add("#10평미만");
                            a = "10평";
                            filter_text_iv.setVisibility(View.INVISIBLE);
//                                init();
                            filter_hash1.setText("#10평미만");
                            booleantrue();
                            i1_1 = false;
                        } else {
                            filterTag.remove("#10평미만");
                            filter_hash1.setText("");
                            a = "";
//                                init2("#10평미만");
                            i1_1 = true;
                        }
                    }
                });
                filter_expandable1_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i1_2) {
                            filterTag.add("#10평대");
                            a = "10평대";
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            filter_hash1.setText("#10평대");
//                                init();
                            booleantrue();
                            i1_2 = false;
                        } else {
                            filterTag.remove("#10평대");
                            filter_hash1.setText("");
                            a = "";
//                                init2("#10평대");
                            i1_2 = true;
                        }
                    }
                });
                filter_expandable1_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i1_3) {
                            filterTag.add("#20평대");
                            a = "20평대";
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            filter_hash1.setText("#20평대");
//                                init();
                            booleantrue();
                            i1_3 = false;
                        } else {
                            filterTag.remove("#20평대");
                            a = "";
                            filter_hash1.setText("");
//                                init2("#20평대");
                            i1_3 = true;
                        }
                    }
                });
                filter_expandable1_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i1_4) {
                            filterTag.add("#30평대");
                            a = "30평대";
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            filter_hash1.setText("#30평대");
//                                init();
                            booleantrue();
                            i1_4 = false;
                        } else {
                            filterTag.remove("#30평대");
                            a = "";
                            filter_hash1.setText("");
//                                init2("#30평대");
                            i1_4 = true;
                        }
                    }
                });
                filter_expandable1_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i1_5) {
                            filterTag.add("#40평대");
                            a = "40평대";
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            filter_hash1.setText("#40평대");
//                                init();
                            booleantrue();
                            i1_5 = false;
                        } else {
                            filterTag.remove("#40평대");
                            a = "";
                            filter_hash1.setText("");
//                                init2("#40평대");
                            i1_5 = true;
                        }
                    }
                });
                filter_expandable1_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i1_6) {
                            filterTag.add("#50평이상");
                            a = "50평이상";
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            filter_hash1.setText("#50평이상");
//                                init();
                            booleantrue();
                            i1_6 = false;
                        } else {
                            filterTag.remove("#50평이상");
                            a = "";
                            filter_hash1.setText("");
//                                init2("#50평이상");
                            i1_6 = true;
                        }
                    }
                });
            } else if (groupPosition == 2) {
                v = LayoutInflater.from(context).inflate(R.layout.item_filter_expandable_2, null);
                filter_expandable2_1 = (ImageView) v.findViewById(R.id.filter_expandable2_1);
                filter_expandable2_2 = (ImageView) v.findViewById(R.id.filter_expandable2_2);
                filter_expandable2_3 = (ImageView) v.findViewById(R.id.filter_expandable2_3);
                filter_expandable2_4 = (ImageView) v.findViewById(R.id.filter_expandable2_4);

                filter_expandable2_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i2_1) {
                            filterTag.add("#원룸");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            b = "원룸";
                            filter_hash2.setText("#원룸");
                            booleantrue2();
//                                init();
                            i2_1 = false;
                        } else {
                            filterTag.remove("#원룸");
                            filter_hash2.setText("");
                            b = "";
//                                init2("#원룸");
                            i2_1 = true;
                        }
                    }
                });
                filter_expandable2_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i2_2) {
                            filterTag.add("#빌라");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            b = "빌라";
                            filter_hash2.setText("#빌라");
                            booleantrue2();
//                                init();
                            i2_2 = false;
                        } else {
                            filterTag.remove("#빌라");
                            filter_hash2.setText("");
                            b = "";
//                                init2("#빌라");
                            i2_2 = true;
                        }
                    }
                });
                filter_expandable2_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i2_3) {
                            filterTag.add("#주택");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            b = "주택";
                            filter_hash2.setText("#주택");
                            booleantrue2();
//                                init();
                            i2_3 = false;
                        } else {
                            filterTag.remove("#주택");
                            filter_hash2.setText("");
                            b = "";
//                                init2("#주택");
                            i2_3 = true;
                        }
                    }
                });
                filter_expandable2_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i2_4) {
                            filterTag.add("#아파트");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            b = "아파트";
                            filter_hash2.setText("#아파트");
                            booleantrue2();
//                                init();
                            i2_4 = false;
                        } else {
                            filterTag.remove("#아파트");
                            filter_hash2.setText("");
                            b = "";
//                                init2("#아파트");
                            i2_4 = true;
                        }
                    }
                });
            } else if (groupPosition == 3) {
                v = LayoutInflater.from(context).inflate(R.layout.item_filter_expandable_3, null);
                filter_expandable3_1 = (ImageView) v.findViewById(R.id.filter_expandable3_1);
                filter_expandable3_2 = (ImageView) v.findViewById(R.id.filter_expandable3_2);
                filter_expandable3_3 = (ImageView) v.findViewById(R.id.filter_expandable3_3);
                filter_expandable3_4 = (ImageView) v.findViewById(R.id.filter_expandable3_4);
                filter_expandable3_5 = (ImageView) v.findViewById(R.id.filter_expandable3_5);
                filter_expandable3_6 = (ImageView) v.findViewById(R.id.filter_expandable3_6);
                filter_expandable3_7 = (ImageView) v.findViewById(R.id.filter_expandable3_7);
                filter_expandable3_8 = (ImageView) v.findViewById(R.id.filter_expandable3_8);
                filter_expandable3_9 = (ImageView) v.findViewById(R.id.filter_expandable3_9);
                filter_expandable3_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (i3_1) {
                            filterTag.add("#침실");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            c = "침실";
                            filter_hash3.setText("#침실");
                            booleantrue3();
//                                init();
                            i3_1 = false;
                        } else {
                            filterTag.remove("#침실");
                            filter_hash3.setText("");
                            c = "";
//                                init2("#침실");
                            i3_1 = true;
                        }
                    }
                });

                filter_expandable3_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (i3_2) {
                            filterTag.add("#거실");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            c = "거실";
                            filter_hash3.setText("#거실");
                            booleantrue3();
//                                init();
                            i3_2 = false;
                        } else {
                            filterTag.remove("#거실");
                            filter_hash3.setText("");
                            c = "";
//                                init2("#거실");
                            i3_2 = true;
                        }
                    }
                });
                filter_expandable3_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i3_3) {
                            filterTag.add("#욕실");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            c = "욕실";
                            filter_hash3.setText("#욕실");
                            booleantrue3();
//                                init();
                            i3_3 = false;
                        } else {
                            filterTag.remove("#욕실");
                            filter_hash3.setText("");
                            c = "";
//                                init2("#욕실");
                            i3_3 = true;
                        }
                    }
                });
                filter_expandable3_4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i3_4) {
                            filterTag.add("#주방");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            c = "주방";
                            filter_hash3.setText("#주방");
                            booleantrue3();
//                                init();
                            i3_4 = false;
                        } else {
                            filterTag.remove("#주방");
                            filter_hash3.setText("");
                            c = "";
//                                init2("#주방");
                            i3_4 = true;
                        }
                    }
                });
                filter_expandable3_5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i3_5) {
                            filterTag.add("#서재");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            c = "서재";
                            filter_hash3.setText("#서재");
                            booleantrue3();
//                                init();
                            i3_5 = false;
                        } else {
                            filterTag.remove("#서재");
                            filter_hash3.setText("");
                            c = "";
//                                init2("#서재");
                            i3_5 = true;
                        }
                    }
                });
                filter_expandable3_6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i3_6) {
                            filterTag.add("#드레스룸");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            c = "드레스룸";
                            filter_hash3.setText("#드레스룸");
                            booleantrue3();
//                                init();
                            i3_6 = false;
                        } else {
                            filterTag.remove("#드레스룸");
                            filter_hash3.setText("");
                            c = "";
//                                init2("#드레스룸");
                            i3_6 = true;
                        }
                    }
                });
                filter_expandable3_7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (i3_7) {
                            filterTag.add("#아이방");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            c = "아이방";
                            filter_hash3.setText("#아이방");
                            booleantrue3();
//                                init();
                            i3_7 = false;
                        } else {
                            filterTag.remove("#아이방");
                            filter_hash3.setText("");
                            c = "";
//                                init2("#아이방");
                            i3_7 = true;
                        }
                    }
                });
                filter_expandable3_8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i3_8) {
                            filterTag.add("#현관/발코니");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            c = "현관/발코니";
                            filter_hash3.setText("#현관/발코니");
                            booleantrue3();
//                                init();
                            i3_8 = false;
                        } else {
                            filterTag.remove("#현관/발코니");
                            filter_hash3.setText("");
                            c = "";
//                                init2("#현관/발코니");
                            i3_8 = true;
                        }
                    }
                });
                filter_expandable3_9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (i3_9) {
                            filterTag.add("#야외");
                            filter_text_iv.setVisibility(View.INVISIBLE);
                            c = "야외";
                            filter_hash3.setText("#야외");
                            booleantrue3();
//                                init();
                            i3_9 = false;
                        } else {
                            filterTag.remove("#야외");
                            filter_hash3.setText("");
                            c = "";
//                                init2("#야외");
                            i3_9 = true;
                        }
                    }
                });
            } else if (groupPosition == 5) {
                v = LayoutInflater.from(context).inflate(R.layout.item_filter_expandablelist_color1, null);
                OCL = new tagClick();
                for (int i = 0; i < 12; i++) {
                    colorTagImageView[i] = (ImageView) v.findViewById(tagImageViewId[i]);
                    int a = i+1;
                    if(MainActivity.colorTagArry.get(0).equals("13_"+a)){
                        colorTagImageView[i].setImageResource(selectedTagImageViewResource[i]);
                    }

                    colorTagImageView[i].setOnClickListener(OCL);
//                        colorTagImageView[position].setImageResource(selectedTagImageViewResource[position]);

                }

            } else if (groupPosition == 6) {
                v = LayoutInflater.from(context).inflate(R.layout.item_filter_expandablelist_color2, null);
                OCL = new tagClick();
                for (int i = 0; i < 12; i++) {
                    floorColorTagImageView[i] = (ImageView) v.findViewById(floorTagImageViewId[i]);
                    if(MainActivity.colorTagArry.get(1).equals("2_"+i)){
                        floorColorTagImageView[i].setImageResource(selectedFloorTagImageViewResource[i]);
                    }
                    floorColorTagImageView[i].setOnClickListener(OCL);

//                            floorColorTagImageView[position].setImageResource(selectedFloorTagImageViewResource[position]);
                }
            }

            return v;
        }

        View.OnClickListener OCL;

        public void resetfloorBtn() {
            for (int i = 0; i < 12; i++) {
                floorColorTagImageView[i].setImageResource(unselectedFloorTagImageViewResource[i]);
            }

        }

        public void resettagBtn() {
//            Log.e("됐땅", "");
            for (int i = 0; i < 12; i++) {
                colorTagImageView[i].setImageResource(unselectedTagImageViewResource[i]);
            }
        }

        public class tagClick implements View.OnClickListener {

            @Override
            public void onClick(View v) {

                for (int i = 0; i < 12; i++) {
                    if (v.getId() == floorTagImageViewId[i]) {
                        resetfloorBtn();
                        MainActivity.colorTagArry.set(1, "2_" + i);
                        floorColorTagImageView[i].setImageResource(selectedFloorTagImageViewResource[i]);
                    }
                    if (v.getId() == tagImageViewId[i]) {
                        resettagBtn();
                        int a = i+1;
                        MainActivity.colorTagArry.set(0, "13_" + a);
                        colorTagImageView[i].setImageResource(selectedTagImageViewResource[i]);
                    }
                }
            }
        }


        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    private void init() {
//        Log.e("ssong test", filterTag + "");
        filter_text_iv.setVisibility(View.INVISIBLE);
        for (int i = 0; i < filterTag.size(); i++) {
            if (i == 0) {
                filter_hash1.setText(filterTag.get(i));
            } else if (i == 1) {
                filter_hash2.setText(filterTag.get(i));
            } else if (i == 2) {
                filter_hash3.setText(filterTag.get(i));
            } else if (i == 3) {
                filter_hash4.setText(filterTag.get(i));
            } else if (i == 4) {
                filter_hash5.setText(filterTag.get(i));
            }

        }

    }

    private void init2(String name) {
//        Log.e("ssong test", filterTag + "");

        init();
        for (int j = filterTag.size(); j < 5; j++) {
            if (j == 0) {
                filter_hash1.setText("");
            } else if (j == 1) {
                filter_hash2.setText("");
            } else if (j == 2) {
                filter_hash3.setText("");
            } else if (j == 3) {
                filter_hash4.setText("");
            } else if (j == 4) {
                filter_hash5.setText("");
            }
        }
    }

    private void booleantrue() {
        i1_1 = true;
        i1_2 = true;
        i1_3 = true;
        i1_4 = true;
        i1_5 = true;
        i1_6 = true;
    }

    private void booleantrue2() {
        i2_1 = true;
        i2_2 = true;
        i2_3 = true;
        i2_4 = true;
    }

    private void booleantrue3() {
        i3_1 = true;
        i3_2 = true;
        i3_3 = true;
        i3_4 = true;
        i3_5 = true;
        i3_6 = true;
        i3_7 = true;
        i3_8 = true;
        i3_9 = true;
    }

    private void booleanfalse() {
        i1_1 = false;
        i1_2 = false;
        i1_3 = false;
        i1_4 = false;
        i1_5 = false;
        i1_6 = false;
        i2_1 = false;
        i2_2 = false;
        i2_3 = false;
        i2_4 = false;
        i3_1 = false;
        i3_2 = false;
        i3_3 = false;
        i3_4 = false;
        i3_5 = false;
        i3_6 = false;
        i3_7 = false;
        i3_8 = false;
        i3_9 = false;
    }

}
