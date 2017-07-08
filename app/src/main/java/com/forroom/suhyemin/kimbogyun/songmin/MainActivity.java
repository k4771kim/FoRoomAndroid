package com.forroom.suhyemin.kimbogyun.songmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.forroom.suhyemin.kimbogyun.songmin.common.DBConstant;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.fragment.MagazineFragment;
import com.forroom.suhyemin.kimbogyun.songmin.fragment.MyRoomFragment;
import com.forroom.suhyemin.kimbogyun.songmin.fragment.WishRoomFragment;
import com.forroom.suhyemin.kimbogyun.songmin.profilePage.ProfileActivity;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.GoodsItemValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MyRoomValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.WishRoomValueObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int easteregg = 20;
    private ViewPager mainViewPager, magazineViewPager;
    private long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private FloatingActionButton fab;
    private TabLayout tabLayout;

    static MainActivity owner;
    private ImageView LeftIcon, rightIcon;
    private Toolbar toolbar;
    //    private static int goodVersion;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    //    public static LoginValueObject loginVO;
    private SQLiteDatabase db;
    private ForoomSQLiteOpenHelper helper;
    private MainActivity ow;
    private AppBarLayout appBarLayout;

    public static final String USER_ID = "userid";
    public static final String USER_NAME = "username";
    public static final String USER_WAY = "userway";
    public static final String GOOD_VERSION = "goodversion";
    public static final String PREF_NAME = "prefs";

    public static ArrayList tagarray = null;
    public static ArrayList colorTagArry = null;
    public static Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;

        //이스터에그

        ImageView easterEgg = (ImageView) findViewById(R.id.imageView16);
        easterEgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("이스터", "에그");
                easteregg--;
                if (easteregg == 0) {
                    easterEgg();
                    easteregg = 20;
                }
            }
        });

        //이스터에그

        ow = this;
        helper = new ForoomSQLiteOpenHelper();
        tagarray = new ArrayList();
        colorTagArry = new ArrayList();

        colorTagArry.add("");
        colorTagArry.add("");
        //select("소파");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPrefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
        appBarLayout = (AppBarLayout) findViewById(R.id.main_appbar);
//        appBarLayout.isVerticalScrollBarEnabled();
//        appBarLayout.setVerticalScrollBarEnabled(true);

//        CoordinatorLayout cd = (CoordinatorLayout)findViewById(R.id.main_content);
//        cd.setVerticalScrollBarEnabled(true);
//        cd.setScrollContainer(false);
//        cd.setNestedScrollingEnabled(true);
//        cd.isVerticalScrollBarEnabled();


//        toolbar.setVerticalScrollBarEnabled(true);
//        ActionBar ab = getSupportActionBar();
        //ab.setLogo(R.drawable.mp_mypageicon);

        //ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        //ab.setHomeAsUpIndicator(R.drawable.mp_mypageicon);
        //ab.setDisplayHomeAsUpEnabled(true);

        //ab.setCustomView(R.layout.actionbar_main);
        View v = getLayoutInflater().inflate(R.layout.fragment_magazine, null);
        magazineViewPager = (ViewPager) v.findViewById(R.id.magazine_viewPager);

        mainViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        if (mainViewPager != null) {
            setupTabViewPager(mainViewPager);
        }

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mainViewPager);
        tabLayout.getTabTextColors();
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));

        fab = (FloatingActionButton) findViewById(R.id.fab);
        // fab.setImageResource(R.drawable.mp_03_floatingactionbutton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabLayout.getSelectedTabPosition() == 1) {
                    Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
                    intent.putExtra("toGallery", "fromMyRoom");
                    startActivityForResult(intent, 164);
                } else if (tabLayout.getSelectedTabPosition() == 2) {
                    Intent intent = new Intent(MainActivity.this, ForRoomFilter.class);
                    intent.putExtra("toFilter", "fromWishRoom");
                    startActivityForResult(intent, 246);
                }
            }
        });

        LeftIcon = (ImageView) findViewById(R.id.main_top_left_icon);
        LeftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("profiletype", "main");
                intent.putExtra("userid", ForRoomApplication.USER_ID);
                startActivity(intent);
            }
        });

        rightIcon = (ImageView) findViewById(R.id.main_top_right_icon);
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForRoomFilter.class);
                intent.putExtra("toFilter2", tabLayout.getSelectedTabPosition());
                intent.putExtra("toFilter", "fromMainActivity");
                startActivityForResult(intent, 123);
            }
        });

        //new AsyncTaskGoodsItemJSONList().execute();
        floatingThread thread = new floatingThread();
        thread.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                fab.hide();
                rightIcon.setVisibility(View.INVISIBLE);
            } else {
                fab.show();
                rightIcon.setVisibility(View.VISIBLE);
            }
        }
    };

    private class floatingThread extends Thread {
        @Override
        public void run() {
            while (true) {

                if (tabLayout.getSelectedTabPosition() == 0) {
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    handler.sendMessage(message);

                } else {
                    Message message = handler.obtainMessage();
                    message.what = 2;
                    handler.sendMessage(message);

                }
                try {
                    Thread.sleep(300);
                } catch (Exception e) {
                    Log.e("Thread", "Exception", e);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("ssong requestcode", requestCode + "");
//        Log.e("ssong resultcode", resultCode + "");


        if (resultCode == ForRoomConstant.FILTER_MYROOM_RESULT_CODE) {
//            Log.e("resultcode ", "myroom filter");
//            Intent intent = getIntent();
//
//            String[] tagarray = intent.getStringArrayExtra("toMain");
//            for(int i=0; i< tagarray.length;i++){
//                Log.e("ssongtag", tagarray[i]);
//            }
            new AsyncTaskMyRoomFilter().execute(tagarray);
        }
        if (resultCode == ForRoomConstant.FILTER_WISHROOM_RESYLT_CODE) {
//            Log.e("resultcode :", "wishRoom filter");

            new AsyncTaskWishRoomFilter().execute(tagarray, colorTagArry);

        }

        if (resultCode == ForRoomConstant.FILTER_TO_MAIN_FINISH_RESULT_CODE) {
//            Log.e("resultcode :", "wishroom write Filter");
            WishRoomFragment.iv.setImageResource(R.drawable.array_select_new);
            WishRoomFragment.rv.setAdapter(null);
            WishRoomFragment.search = true;
            new WishRoomFragment.AsyncWishRoomJSONList().execute(ForRoomApplication.USER_ID , "1" , "");
        }
        if (resultCode == ForRoomConstant.WISHROOM_INFO_BACK_FINISH_RESULT_CODE) {
//            Log.e("resultcode :", "wishroom info back");
//            new WishRoomFragment.AsyncWishRoomJSONList().execute(ForRoomApplication.USER_ID , "1" , "");
//            new WishRoomFragment.AsyncWishRoomJSONList().execute(ForRoomApplication.USER_ID);
        }
        if(resultCode == ForRoomConstant.GALLERY_TO_MYROOM_LIST) {
//            Log.e("resultcode :", "myroom write back");

            MyRoomFragment.iv.setImageResource(R.drawable.array_select_new);
            MyRoomFragment.rv.setAdapter(null);
            MyRoomFragment.search = true;
            new MyRoomFragment.AsyncTaskMyRoomJSONList().execute(ForRoomApplication.USER_ID, "1" , "");
        }

        if(resultCode == ForRoomConstant.MYROOM_INFO_BACK_FINISH_RESULT_CODE){
//            new MyRoomFragment.AsyncTaskMyRoomJSONList().execute(ForRoomApplication.USER_ID);
        }

    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        long intervalTime = currentTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = currentTime;
            Toast.makeText(getApplicationContext(), "뒤로 버튼 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupTabViewPager(ViewPager viewPager) {
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(MagazineFragment.createInstance(), "매 거 진");
        pagerAdapter.addFragment(MyRoomFragment.createInstance(), "마 이 룸");
        pagerAdapter.addFragment(WishRoomFragment.createInstance(), "위 시 룸");
        viewPager.setAdapter(pagerAdapter);
    }

    static class PagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }




    public class AsyncTaskMyRoomFilter extends AsyncTask<ArrayList, Integer, List<MyRoomValueObject>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<MyRoomValueObject> doInBackground(ArrayList... params) {
//            String[] taglist = params[0];
            ArrayList taglist = params[0];

            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            ArrayList<MyRoomValueObject> myRoomList = null;

            StringBuilder requestQuery = new StringBuilder();
            try {
                requestQuery.append("usrid=" + ForRoomApplication.USER_ID);

//                Log.e("ssong tag", taglist.get(0) + "");
//                Log.e("ssong tag", taglist.get(1) + "");
//                Log.e("ssong tag", taglist.get(2) + "");
                if (taglist.get(0) != "" && taglist.get(0) != null) {
                    requestQuery.append("&tag1=" + taglist.get(0));
                }
                if (taglist.get(1) != "" && taglist.get(1) != null) {
                    requestQuery.append("&tag2=" + taglist.get(1));
                }
                if (taglist.get(2) != "" && taglist.get(2) != null) {
                    requestQuery.append("&tag3=" + taglist.get(2));
                }

                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.MYROOM_FILTER_PARH);
                conn = (HttpURLConnection) target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                OutputStream toServer = conn.getOutputStream();
                toServer.write(new String(requestQuery.toString()).getBytes("UTF-8"));
                toServer.close();

                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = null;
                    StringBuilder jsonBuf = new StringBuilder();
                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }
//                    Log.e("ssspmgpdsmgp", jsonBuf + "");
                    myRoomList = ParseDataParseHandler.getJSONMyRoomRequestAllList(jsonBuf);
                }

            } catch (Exception e) {
                Log.i("AsynctaskMyroom", "dd", e);
            } finally {
                if (fromServer != null) {
                    try {
                        fromServer.close();
                    } catch (Exception e) {
                    }
                    conn.disconnect();
                }
            }
            return myRoomList;
        }

        @Override
        protected void onPostExecute(List<MyRoomValueObject> myRoomValueObjects) {
            super.onPostExecute(myRoomValueObjects);
//            for(int i=0; i<tagarray.size();i++){
            while (!tagarray.isEmpty()) {
//                Log.e("ssong removesize", tagarray.size() + "");
//                Log.e("ssong remove", tagarray.get(0) + "");
                tagarray.remove(0);
            }
            MyRoomFragment mf = new MyRoomFragment();
            mf.recyclerviewinit((ArrayList<MyRoomValueObject>) myRoomValueObjects);
        }
    }

    public class AsyncTaskWishRoomFilter extends AsyncTask<ArrayList, Integer, List<WishRoomValueObject>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<WishRoomValueObject> doInBackground(ArrayList... params) {
//            String[] taglist = params[0];
            ArrayList taglist = params[0];
            ArrayList colorTagList = params[1];
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            ArrayList<WishRoomValueObject> wishRoomList = null;

            StringBuilder requestQuery = new StringBuilder();
            try {
                requestQuery.append("usrid=" + ForRoomApplication.USER_ID);


//                Log.e("ssong tag", taglist.get(0) + "");
//                Log.e("ssong tag", taglist.get(1) + "");
//                Log.e("ssong tag", taglist.get(2) + "");

                if (taglist.get(0) != "" && taglist.get(0) != null) {
                    Log.e("ssong tag1", taglist.get(0)+"");
                    requestQuery.append("&tag1=" + taglist.get(0));
                }
                if (taglist.get(1) != "" && taglist.get(1) != null) {
                    Log.e("ssong tag2", taglist.get(1)+"");
                    requestQuery.append("&tag2=" + taglist.get(1));
                }
                if (taglist.get(2) != "" && taglist.get(2) != null) {
                    Log.e("ssong tag3", taglist.get(2)+"");
                    requestQuery.append("&tag3=" + taglist.get(2));
                }
                if (colorTagList.get(0) != "") {
                    Log.e("ssong tag4", colorTagList.get(0)+"");
                    requestQuery.append("&tag4=" + colorTagList.get(0));
                }
                if (colorTagList.get(1) != "") {
                    Log.e("ssong tag5", colorTagList.get(1)+"");
                    requestQuery.append("&tag5=" + colorTagList.get(1));
                }
//                Log.e("쿼리", requestQuery + "");
                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.WISHROOM_FILTER_PATH);
                conn = (HttpURLConnection) target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                OutputStream toServer = conn.getOutputStream();
                toServer.write(new String(requestQuery.toString()).getBytes("UTF-8"));
                toServer.close();

                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = null;
                    StringBuilder jsonBuf = new StringBuilder();
                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }
//                    Log.e("ssspmgpdsmgp", jsonBuf + "");
                    wishRoomList = ParseDataParseHandler.getJSONWishRoomRequestAllList(jsonBuf);
                }

            } catch (Exception e) {
                Log.i("AsynctaskMyroom", "dd", e);
            } finally {
                if (fromServer != null) {
                    try {
                        fromServer.close();
                    } catch (Exception e) {
                    }
                    conn.disconnect();
                }
            }
            return wishRoomList;
        }

        @Override
        protected void onPostExecute(List<WishRoomValueObject> wishRoomValueObjects) {
            super.onPostExecute(wishRoomValueObjects);
//            for(int i=0; i<tagarray.size();i++){
            while (!tagarray.isEmpty()) {
//                Log.e("ssong removesize", tagarray.size() + "");
//                Log.e("ssong remove", tagarray.get(0) + "");
                tagarray.remove(0);
            }
            colorTagArry.clear();
            colorTagArry.add("");
            colorTagArry.add("");
            WishRoomFragment wf = new WishRoomFragment();
            wf.recyclerviewinit((ArrayList<WishRoomValueObject>) wishRoomValueObjects);
        }
    }

    public ArrayList<GoodsItemValueObject> select(String s) {
        ArrayList<GoodsItemValueObject> list;
        helper = new ForoomSQLiteOpenHelper();
//        db = helper.getReadableDatabase();
        db = helper.getWritableDatabase();
//        Cursor c = db.query(DBConstant.TABLE_NAME, null, null, null, null, null, null);
        Cursor c = db.rawQuery("SELECT * FROM " + DBConstant.TABLE_NAME + " WHERE " +
                DBConstant.GOODS_SERIES + " = " + "'" + s + "'", null);
        list = new ArrayList<>();
        while (c.moveToNext()) {
            GoodsItemValueObject gVO = new GoodsItemValueObject();
            gVO.goods_id = c.getString(c.getColumnIndex(DBConstant.GOODS_ID));
//            Log.e("ssongvvvvvv", gVO.goods_id);
            gVO.goods_series = c.getString(c.getColumnIndex(DBConstant.GOODS_SERIES));
//            Log.e("ssongwwwwwww", gVO.goods_series);
            gVO.goods_image = c.getString(c.getColumnIndex(DBConstant.GOODS_IMAGE));
            Log.e("ssonguuuuuuu", gVO.goods_image);
            list.add(gVO);
        }
        return list;
    }


    /////이스터에그


    int select = 0;
    private AlertDialog.Builder ab;
    final String items[] = {"Panorama", "VR"};

    public void easterEgg() {
        select = 0;
        ab = new AlertDialog.Builder(MainActivity.this);
        ab.setCancelable(false);
        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {




                    public void onClick(DialogInterface dialog, int whichButton) { //리스트

                        select = whichButton;
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) { //OK

                        if (select == 0) {
                            new PanoramaAsync().execute(ForRoomConstant.TARGET_URL + "/images/easteregg.jpg" , "FoRoom_Tacademy_EasterEgg_By_KBG.jpg");

//
//                            Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
//                            intent.putExtra("imgpath", path);
//                            startActivity(intent);

                        } else if (select == 1) {
                            new VRAsync().execute(ForRoomConstant.TARGET_URL + "/images/easteregg.jpg" , "FoRoom_Tacademy_EasterEgg_By_KBG.jpg");

                        }

                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancel 버튼 클릭시
                        dialog.dismiss();
                    }
                });

        ab.show();
    }



    public class VRAsync extends AsyncTask<String, Integer, String> { //VR 실행
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(MainActivity.this, "로딩중", "잠시 기다려주세요", true);
            pd.show();
        }

        @Override
        protected String doInBackground(String... arg) {
            String filePath = null;
            try {
                filePath = exdownloadFile(arg[0], arg[1]);
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            if (s != null) {
                Intent intent = new Intent(MainActivity.this, vrActivity.class);
                intent.putExtra("imgpath", s);
                startActivity(intent);
            }
        }
    }

    public class PanoramaAsync extends AsyncTask<String, Integer, String> { //파노라마 실행
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = ProgressDialog.show(MainActivity.this, "로딩중", "잠시 기다려주세요", true);
            pd.show();
        }

        @Override
        protected String doInBackground(String... arg) {
            String filePath =null;
            try {
                filePath = exdownloadFile(arg[0], arg[1]);
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            if (s != null) {
                Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
                intent.putExtra("imgpath", s);
                startActivity(intent);
            }
        }
    }

    String exdownloadFile(String strUrl, String fileName) { //외부저장소에 저장.
        int count;
        String filePath;
        try {
            URL url = new URL(strUrl);
            URLConnection conection = url.openConnection();
            conection.connect();
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "foroom");
            if (!directory.exists()) {
                directory.mkdir();
            }

            File file = new File(directory + File.separator + fileName);
            if (!file.exists()) {
//                Log.e("다운로드", "^^");
                OutputStream output = new FileOutputStream(directory + File.separator + fileName);

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } else {

//                Log.e("노 다운로드", " ^^");
            }
            filePath = file.toString();
        } catch (Exception e) {
            Log.d("tag", "Image download error."+e);
            return null;
        }
        return filePath;
    }



}

