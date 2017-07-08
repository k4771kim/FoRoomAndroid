package com.forroom.suhyemin.kimbogyun.songmin.profilePage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forroom.suhyemin.kimbogyun.songmin.ForRoomApplication;
import com.forroom.suhyemin.kimbogyun.songmin.GalleryActivity;
import com.forroom.suhyemin.kimbogyun.songmin.ParseDataParseHandler;
import com.forroom.suhyemin.kimbogyun.songmin.R;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MagazineValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MyRoomValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.ProfileWriteValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.UserInfoValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.WishRoomValueObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {
    FrameLayout frame;
    public static File galleryFile;
    private CircleImageView civ;
    private TextView nick, desc, scrapcount, writecount, jjimcount;
    private RecyclerView rv;
    private ProfileWriteValueObject PV;
    public static UserInfoValueObject userVO;
    private ExpandableListView profileexpandable;
//    private ProfileExpandableAdapter expandableAdapter;
//    private ProfileExpandableAdapter2 expandableAdapter2;
    private ProfileRecyclerAdapter adapter =null;
    private List<MagazineValueObject> magazinelist;
    private List<MyRoomValueObject> myroomlist;
    private List<WishRoomValueObject> wishroomlist;
    private List<MyRoomValueObject> writemyroomlist;
    private List<WishRoomValueObject> writewishroomlist;
    private LinearLayout scrapLinear, writeLinear, jjimLinear;
    private ImageView leftIcon, rightIcon;
    private ImageView changeMarker;
    private TabLayout profiletablayout;
    private ViewPager profileviewpager;
    private TextView scrapText, writeText, jjimText;
    public static String userid;
    private String profiletype;
    public static Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mActivity = this;
        rightIcon = (ImageView)findViewById(R.id.profile_top_right);
        profiletablayout = (TabLayout)findViewById(R.id.profile_tablayout);
        profileviewpager = (ViewPager)findViewById(R.id.profile_viewpager);
        scrapText = (TextView)findViewById(R.id.profile_scrapcount);
        writeText = (TextView)findViewById(R.id.profile_writecount);
        jjimText = (TextView)findViewById(R.id.profile_jjimcount);

        if (profileviewpager != null) {
            setupTabViewPager(profileviewpager);
        }
        profiletablayout.setupWithViewPager(profileviewpager);
//        profiletablayout.setSelectedTabIndicatorColor(Color.parseColor("#000000"));
        profiletablayout.setTabTextColors(Color.parseColor("#9b9b9b"), Color.parseColor("#010101"));
        profiletablayout.setSelectedTabIndicatorHeight(0);
        profileviewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
//                Log.e("ssong position", position+"");
                if(position==0) {
                    scrapText.setTextColor(Color.parseColor("#010101"));
                    writeText.setTextColor(Color.parseColor("#9b9b9b"));
                    jjimText.setTextColor(Color.parseColor("#9b9b9b"));
                }else if(position ==1){
                    scrapText.setTextColor(Color.parseColor("#9b9b9b"));
                    writeText.setTextColor(Color.parseColor("#010101"));
                    jjimText.setTextColor(Color.parseColor("#9b9b9b"));
                }else{
                    scrapText.setTextColor(Color.parseColor("#9b9b9b"));
                    writeText.setTextColor(Color.parseColor("#9b9b9b"));
                    jjimText.setTextColor(Color.parseColor("#010101"));
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        leftIcon = (ImageView)findViewById(R.id.profile_left_icon);
        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        profiletype = intent.getStringExtra("profiletype");
        userid = intent.getStringExtra("userid");

        changeMarker = (ImageView)findViewById(R.id.profile_change_mark);
        PV = new ProfileWriteValueObject();
        frame = (FrameLayout)findViewById(R.id.profile_frame);
        civ = (CircleImageView)findViewById(R.id.profile_circleimageview);
        nick = (TextView)findViewById(R.id.profile_nickname);
        desc = (TextView)findViewById(R.id.profile_desc);
        scrapcount = (TextView)findViewById(R.id.profile_scrapcount);
        writecount = (TextView)findViewById(R.id.profile_writecount);
        jjimcount = (TextView)findViewById(R.id.profile_jjimcount);

        new AsyncTaskProfileInfoSet().execute(userid);
        if(profiletype.equalsIgnoreCase("main")){
            changeMarker.setVisibility(View.VISIBLE);
            rightIcon.setVisibility(View.VISIBLE);
            frame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfileActivity.this, GalleryActivity.class);
                    intent.putExtra("toGallery", "fromProfile");
                    startActivityForResult(intent, 0);

                }
            });

            nick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.dialog_profile_nickname, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                    builder.setView(dialogView);
                    final AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                    ImageView iv2 = (ImageView)dialogView.findViewById(R.id.profile_nick_cancle);
                    ImageView iv1 = (ImageView)dialogView.findViewById(R.id.profile_nick_ok);

                    iv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText edit_name = (EditText) dialogView.findViewById(R.id.profile_nick_edittext);
                            String name = edit_name.getText().toString();
                            PV.profile_nickname = name;
                            new AsyncTaskNickChange().execute(PV);

                            dialog.dismiss();

                            nick.setText(name);
                        }
                    });

                    iv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                        Toast.makeText(ForRoomApplication.getFRContext(), "취소눌름", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }
            });

            desc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.dialog_profile_desc, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                    builder.setView(dialogView);
                    final AlertDialog dialog = builder.create();
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                    ImageView iv2 = (ImageView) dialogView.findViewById(R.id.profile_desc_cancle);
                    ImageView iv1 = (ImageView) dialogView.findViewById(R.id.profile_desc_ok);

                    iv1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText edit_name = (EditText) dialogView.findViewById(R.id.profile_desc_edittext);
                            String name = edit_name.getText().toString();
                            PV.profile_desc = name;

                            new AsyncTaskDescChange().execute(PV);
                            dialog.dismiss();

                            desc.setText(name);
                        }
                    });

                    iv2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                        Toast.makeText(ForRoomApplication.getFRContext(), "취소눌름", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }
            });
        }else{
            changeMarker.setVisibility(View.GONE);
            rightIcon.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("ssong requestcode", requestCode + "");
//        Log.e("ssong resultcode", resultCode+"");
        if(resultCode== ForRoomConstant.GALLAY_RESULT_CODE){
//            Log.e("ssong result if", "dd");
            Glide.with(ProfileActivity.this).load(galleryFile).skipMemoryCache(true).into(civ);
//            civ.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(galleryFile)));
            PV.profile_image = galleryFile;
            new AsyncTaskProfileImageChange().execute(PV);
        }


    }
    public class AsyncTaskProfileInfoSet extends AsyncTask<String, Integer, UserInfoValueObject>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected UserInfoValueObject doInBackground(String... params) {
            userVO = null;
            String requestQuery = "id="+params[0];
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            try{
                URL target = new URL(ForRoomConstant.TARGET_URL+ForRoomConstant.PROFILE_USER_INFO);
                conn = (HttpURLConnection)target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                OutputStream toServer = conn.getOutputStream();
                toServer.write(requestQuery.getBytes("UTF-8"));
                toServer.close();
                int resCode = conn.getResponseCode();
                if(resCode == HttpURLConnection.HTTP_OK){
                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = null;
                    StringBuilder jsonBuf = new StringBuilder();
                    while((line = fromServer.readLine())!=null){
                        jsonBuf.append(line);
                    }
                    userVO = ParseDataParseHandler.getJSONProfileRequestAllList(jsonBuf);
                }
            }catch(Exception e){

            }finally {
                if(fromServer != null){
                    try {
                        fromServer.close();
                    }catch(Exception e){
                    }
                    conn.disconnect();
                }
            }
            return userVO;
        }

        @Override
        protected void onPostExecute(UserInfoValueObject s) {
            nick.setText(s.usr_name);
            desc.setText(s.usr_desc);
            if(desc.length()==0){
                desc.setText("자기소개를 입력하세요.");
            }
            rightIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(ForRoomApplication.getFRContext(), "준비중입니다", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProfileActivity.this, ConfigActivity.class);
                    startActivity(intent);

                }
            });
            scrapcount.setText(s.usr_likecount);
            writecount.setText(s.usr_writecount);
            jjimcount.setText(s.usr_jjimcount);
            Glide.with(ProfileActivity.this).load(ForRoomConstant.TARGET_URL+s.usr_pic).error(R.drawable.profile).skipMemoryCache(true).into(civ);
        }
    }


    public class AsyncTaskProfileImageChange extends AsyncTask<ProfileWriteValueObject, Integer,String> {
        private final MediaType MEDiA_TYPE_PNG = MediaType.parse("image/png");
        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(ProfileWriteValueObject... params) {
            ProfileWriteValueObject param = params[0];
            String result = "";
            File file = new File(param.profile_image.toString());

            MultipartBody.Builder mb = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addPart(
                            Headers.of("Content-Disposition", "form-data; name=\"usrid\""),
                            RequestBody.create(null, userVO.usr_id));
            mb.addFormDataPart("picture", file.getName(), RequestBody.create(MEDiA_TYPE_PNG, file));
            RequestBody body = mb.build();

            Request request = new Request.Builder()
                    .url(ForRoomConstant.TARGET_URL+ForRoomConstant.PROFILE_CHANGE_IMAGE)
                    .post(body)
                    .build();
            Response response = null;
            try{
                response = client.newCall(request).execute();
                return response.body().string();
            }catch(Exception e){
                Log.e("profileImageChange", "에러 서버가 잘못함", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Log.e("ssong profileImage!", s);
            JSONObject responseData = null;
            String result = null;
            try{
                responseData = new JSONObject(s);
                result = responseData.getString("result");
            }catch(Exception e){
//                Log.e("ssong 에러", "ㅇㅇㅇㅇㅇ", e);
            }

            if(s!= null){
                if(result.equalsIgnoreCase("SUCCESS")){
//                    Toast.makeText(ForRoomApplication.getFRContext(), "글이 잘 입력 되었다", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class AsyncTaskNickChange extends AsyncTask<ProfileWriteValueObject, Integer, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(ProfileWriteValueObject... params) {
            ProfileWriteValueObject param = params[0];
            String result ="";
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            StringBuilder queryBuf = new StringBuilder();

            try{
                queryBuf.append("id="+userVO.usr_id)
                        .append("&nick="+param.profile_nickname);

                URL target = new URL(ForRoomConstant.TARGET_URL+ForRoomConstant.PROFILE_CHANGE_NICK);
                conn = (HttpURLConnection)target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                OutputStream toServer = conn.getOutputStream();
                toServer.write(new String(queryBuf.toString()).getBytes("UTF-8"));
                toServer.close();

                int resCode = conn.getResponseCode();
                if(resCode == HttpURLConnection.HTTP_OK){
                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String onLine ="";

                    StringBuilder jsonBuf = new StringBuilder();
                    while((onLine = fromServer.readLine())!=null){
                        jsonBuf.append(onLine);
                    }
                    JSONObject responseData = new JSONObject(jsonBuf.toString());
                    result = responseData.getString("result");
                }
            }catch(Exception e){
                Log.e("ssong error", "asynctask profilenick", e);
            }finally {
                if(fromServer != null){
                    try {
                        fromServer.close();
                    }catch(Exception e){
                    }if(conn!=null){
                        conn.disconnect();
                    }
                }
            }
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Log.e("ssong test", s);


            if(s!= null){
                if(s.equalsIgnoreCase("SUCCESS")){
//                    Toast.makeText(ForRoomApplication.getFRContext(), "글이 잘 입력 되었다", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class AsyncTaskDescChange extends AsyncTask<ProfileWriteValueObject, Integer, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(ProfileWriteValueObject... params) {
            ProfileWriteValueObject param = params[0];
            String result ="";
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            StringBuilder queryBuf = new StringBuilder();

            try{
                queryBuf.append("id="+userVO.usr_id)
                        .append("&desc=" + param.profile_desc);

                URL target = new URL(ForRoomConstant.TARGET_URL+ForRoomConstant.PROFILE_CHANGE_DESC);
                conn = (HttpURLConnection)target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                OutputStream toServer = conn.getOutputStream();
                toServer.write(new String(queryBuf.toString()).getBytes("UTF-8"));
                toServer.close();

                int resCode = conn.getResponseCode();
                if(resCode == HttpURLConnection.HTTP_OK){
                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String onLine ="";

                    StringBuilder jsonBuf = new StringBuilder();
                    while((onLine = fromServer.readLine())!=null){
                        jsonBuf.append(onLine);
                    }
                    JSONObject responseData = new JSONObject(jsonBuf.toString());
                    result = responseData.getString("result");
                }
            }catch(Exception e){
                Log.e("ssong error", "asynctask profilenick", e);
            }finally {
                if(fromServer != null){
                    try {
                        fromServer.close();
                    }catch(Exception e){
                    }if(conn!=null){
                        conn.disconnect();
                    }
                }
            }
            return result;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Log.e("ssong test", s);
            if(s!= null){
                if(s.equalsIgnoreCase("SUCCESS")){
//                    Toast.makeText(ForRoomApplication.getFRContext(), "글이 잘 입력 되었다", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class ProfileRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private Context context;
        private List<MagazineValueObject> magazineItem;
        private List<MyRoomValueObject> myroomItem;
        private List<WishRoomValueObject> wishroomItem;
        int type =0;
        private static final int TYPE_1=0;
        private static final int TYPE_2=1;
        private static final int TYPE_3=2;


        ProfileRecyclerAdapter(Context context,List<MagazineValueObject> list,List<MyRoomValueObject> list2
                , List<WishRoomValueObject> list3, int i){
            this.context = context;
            type = i;
            if(i==0){
                this.magazineItem = list;
            }else if(i==1){
                this.myroomItem = list2;
            }else if(i==2){
                this.wishroomItem = list3;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch(viewType){
                case TYPE_1:
                    View v1= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_recycler_type1,parent,false);
                    return new MagazineScrapViewHolder(v1);
                case TYPE_2:
                    View v2= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_recycler_type2,parent,false);
                    return new MyRoomScrapViewHolder(v2);
                case TYPE_3:
                    View v3= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_recycler_type3,parent,false);
                    return new WishRoomScrapViewHolder(v3);
            }
            return null;
        }

        @Override
        public int getItemViewType(int position) {
            if(type==0){
                return TYPE_1;
            }else if(type ==1){
                return TYPE_2;
            }else{
                return TYPE_3;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (type){
                case TYPE_1:
                    final MagazineValueObject mgzData = magazineItem.get(position);

                    MagazineScrapViewHolder holder1 = (MagazineScrapViewHolder)holder;
                    Glide.with(context).load(ForRoomConstant.TARGET_URL+mgzData.magazineImage).into(holder1.scrap_magazine_image);
                    break;
                case TYPE_2:
                    final MyRoomValueObject myroomData = myroomItem.get(position);

                    MyRoomScrapViewHolder holder2 = (MyRoomScrapViewHolder)holder;
                    holder2.title.setText(myroomData.title);
                    holder2.nick.setText(myroomData.nickname+"'s style");
//                    Log.e("dddd", myroomData.hash.length+"dd");
                    for(int i=0; i<myroomData.hash.length;i++){
                        switch(i){
                            case 0:
                                holder2.hash1.setText("#"+myroomData.hash[i]);
                            case 1:
                                holder2.hash2.setText("#"+myroomData.hash[i]);
                            case 2:
                                holder2.hash3.setText("#" + myroomData.hash[i]);
                        }
                    }
//                    Log.e("ssong", myroomData.myhouse[0]);
                    Glide.with(ForRoomApplication.getFRContext()).load(ForRoomConstant.TARGET_URL+myroomData.myhouse[0]).into(holder2.scrap_myroom_image);
                    break;
                case TYPE_3:
                    final WishRoomValueObject wishData = wishroomItem.get(position);

                    WishRoomScrapViewHolder holder3 = (WishRoomScrapViewHolder)holder;
                    Glide.with(context).load(ForRoomConstant.TARGET_URL+wishData.wishhouse).into(holder3.scrap_wishroom_image);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            if(type==0){
                return magazineItem.size();
            }else if(type==1){
                return myroomItem.size();
            }else if(type==2){
                return wishroomItem.size();
            }
            return 0;
        }


        public class MagazineScrapViewHolder extends RecyclerView.ViewHolder{
            public View mview;
            public ImageView scrap_magazine_image;

            public MagazineScrapViewHolder(View v){
                super(v);
                mview = v;
                scrap_magazine_image = (ImageView)v.findViewById(R.id.profile_recycler_type1_image);
            }
        }
        public class MyRoomScrapViewHolder extends RecyclerView.ViewHolder{
            public View mview;
            public ImageView scrap_myroom_image;
            public TextView title, nick, hash1, hash2, hash3;

            public MyRoomScrapViewHolder(View v){
                super(v);
                mview = v;
                scrap_myroom_image = (ImageView)v.findViewById(R.id.profile_recycler_type2_image);
                title = (TextView)v.findViewById(R.id.profile_recycler_type2_title);
                nick = (TextView)v.findViewById(R.id.profile_recycler_type2_nick);
                hash1 = (TextView)v.findViewById(R.id.profile_recycler_type2_hash1);
                hash2 = (TextView)v.findViewById(R.id.profile_recycler_type2_hash2);
                hash3 = (TextView)v.findViewById(R.id.profile_recycler_type2_hash3);
            }
        }
        public class WishRoomScrapViewHolder extends RecyclerView.ViewHolder{
            public View mview;
            public ImageView scrap_wishroom_image;

            public WishRoomScrapViewHolder(View v){
                super(v);
                mview = v;
                scrap_wishroom_image = (ImageView)v.findViewById(R.id.profile_recycler_type3_image);
            }
        }
    }

    private void setupTabViewPager(ViewPager viewPager){
        final ProfileAdapter pagerAdapter = new ProfileAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(ProfileScrapFragment.createInstance(), "스 크 랩");
        pagerAdapter.addFragment(ProfileWriteFragment.createInstance(),"작 성 글");
        pagerAdapter.addFragment(ProfileJjimFragment.createInstance(),"장 바 구 니");
        viewPager.setAdapter(pagerAdapter);
    }
    private static class ProfileAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public ProfileAdapter(FragmentManager fragmentManager) {
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
}

