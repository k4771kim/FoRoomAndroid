package com.forroom.suhyemin.kimbogyun.songmin.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.forroom.suhyemin.kimbogyun.songmin.ForRoomApplication;
import com.forroom.suhyemin.kimbogyun.songmin.MainActivity;
import com.forroom.suhyemin.kimbogyun.songmin.ParseDataParseHandler;
import com.forroom.suhyemin.kimbogyun.songmin.R;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MagazineValueObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class MagazineFragment extends Fragment {
    private ViewPager viewPager;
    static MainActivity owner;
    private View v;
    private View v2;
    private ImageView magazineImage;
    private TextView mgz_title;
    private TextView mgz_subtitle;
    public static List<MagazineValueObject> magazineVO;
    public static int viewpagerposition;

    public MagazineFragment() {
        // Required empty public constructor
    }

    public static MagazineFragment createInstance() {
        final MagazineFragment pageFragment = new MagazineFragment();
        final Bundle bundle = new Bundle();
        pageFragment.setArguments(bundle);

        return pageFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_magazine, container, false);
        viewPager = (ViewPager)v.findViewById(R.id.magazine_viewPager);
        v2 = inflater.inflate(R.layout.fragment_magazine_item, container, false);

        magazineImage = (ImageView)v2.findViewById(R.id.magazineImageItem);
        mgz_title = (TextView)v.findViewById(R.id.magazine_Title);
        mgz_subtitle = (TextView)v.findViewById(R.id.magazine_subTitle);

        View BottomLinear = (LinearLayout)v.findViewById(R.id.mainmagazineBottom);
        BottomSheetLayout bottomSheetLayout = (BottomSheetLayout)v.findViewById(R.id.bottomsheet);
        BottomLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ForRoomApplication.getFRContext(), "아직 서버에서 값 받아오는게아님", Toast.LENGTH_SHORT).show();

                new MagazineList_item().show(getFragmentManager(), R.id.bottomsheet);
            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
//                Log.e("ssong pageSelected", position+"");
                viewpagerposition = position;
                if(position==0){
                    mgz_title.setText("Style Magazine");
                    mgz_subtitle.setText("다양한 인테리어 스타일을 전합니다.");
                }else if(position ==1){
                    mgz_title.setText("Brand Magazine");
                    mgz_subtitle.setText("다양한 인테리어 브랜드를 전합니다.");
                }else if(position ==2){
                    mgz_title.setText("Street Magazine");
                    mgz_subtitle.setText("다양한 인테리어 거리를 전합니다.");
                }else if(position ==3){
                    mgz_title.setText("Color Magazine");
                    mgz_subtitle.setText("다양한 컬러 이야기를 전합니다.");
                }else if(position ==4){
                    mgz_title.setText("Know-how Magazine");
                    mgz_subtitle.setText("다양한 인테리어 노하우를 전합니다.");
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        viewPager.setClipToPadding(false);

        viewPager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.magazine_margin));

        new AsyncMagazineJSONList().execute();

        return v;
    }

    public class AsyncMagazineJSONList extends AsyncTask<String , Integer, ArrayList<MagazineValueObject>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<MagazineValueObject> doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader fromserver = null;
            ArrayList<MagazineValueObject> magazineList = null;
            try{
                URL target = new URL(ForRoomConstant.TARGET_URL+ForRoomConstant.MAGAZINE_RECENT_LIST_PATH);
                conn = (HttpURLConnection)target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);

                int resCode = conn.getResponseCode();
                if(resCode == HttpURLConnection.HTTP_OK){
                    fromserver = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = null;
                    StringBuilder jsonBuf = new StringBuilder();

                    while((line=fromserver.readLine())!=null){
                        jsonBuf.append(line);
                    }
                    magazineList = ParseDataParseHandler.getJSONMagazineRequestAllList(jsonBuf);

                }


            }catch(Exception e){
                Log.e("magazine ssong", "오류", e);
            }finally {
                if(fromserver != null){
                    try{
                        fromserver.close();
                    }catch(Exception e){
                    }
                    conn.disconnect();
                }
            }
            return magazineList;
        }

        @Override
        protected void onPostExecute(ArrayList<MagazineValueObject> magazineValueObjects) {
            if(magazineValueObjects != null && magazineValueObjects.size()>0) {
                viewPager.setAdapter(new MagazineAdapter(ForRoomApplication.getFRContext(), magazineValueObjects));
            }
        }
    }

    public class MagazineAdapter extends FragmentPagerAdapter {

        private Context context;
        //private List<MagazineValueObject> magazineItem;

        MagazineAdapter(Context context, List<MagazineValueObject> magazinevo) {
            super(getChildFragmentManager());
            this.context = context;
            //this.magazineItem = magazinevo;
            magazineVO = magazinevo;
        }
        @Override
        public Fragment getItem(int position) {
/*            MagazineValueObject MAVO = magazineVO.get(position);
            Glide.with(context).
                    load(ForRoomConstant.TARGET_URL + MAVO.magazineImage).into(magazineImage);
            mgz_title.setText(MAVO.mg_title);
            mgz_subtitle.setText(MAVO.mg_seriesNo);

            return new Magazine_Item_Fragment();*/

            switch(position){
                case 0:
                    return new Magazine_Item_Fragment();
                case 1:
                    return new Magazine_Item2_Fragment();
                case 2:
                    return new Magazine_Item3_Fragment();
                case 3:
                    return new Magazine_Item4_Fragment();
                case 4:
                    return new Magazine_Item5_Fragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return magazineVO.size();
        }


    }
}

