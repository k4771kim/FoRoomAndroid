package com.forroom.suhyemin.kimbogyun.songmin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.profilePage.ProfileActivity;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.WishRoomInfoValueObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class WishRoom_info extends AppCompatActivity {
    private TextView wr_info_title, wr_info_nick, wr_info_hash1, wr_info_hash2, wr_info_hash3;
    private ImageView wr_info_houseImage, wr_info_reply_icon, wr_info_scrap_icon, wr_info_share_icon;
    private TextView wr_info_scrap_text, wr_info_reply_text;
    private CircleImageView wr_info_profile;
    private String id;
    private static WishRoomInfoValueObject WRIVO;
    private String[] putCommentIntnet = new String[2];
    private RecyclerView rv;
    private Context context;
    private boolean scrapCheck;
    private WishRoomInfoRecyclerAdapter wishroomRecyclerAdapter;
    private ImageView leftIcon;
    private LinearLayout scraplinear, replylinear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_room_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        leftIcon = (ImageView)findViewById(R.id.wishroom_info_left_icon);
        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        WishRoomInfoValueObject WRIVO = WRIVOList.get(0);

        Intent intent = getIntent();
        id = intent.getStringExtra("toWishRoomInfo");
        scrapCheck = intent.getBooleanExtra("toWishRoomInfo2", false);
        Log.i("ssong id!!!!!",id);
        scraplinear = (LinearLayout)findViewById(R.id.wr_info_scrap_linear);
        replylinear = (LinearLayout)findViewById(R.id.wr_info_reply_linear);
        wr_info_share_icon = (ImageView)findViewById(R.id.wr_info_share_icon);
        wr_info_title = (TextView)findViewById(R.id.wr_info_title);
        wr_info_nick = (TextView)findViewById(R.id.wr_info_nick);
        wr_info_hash1 = (TextView)findViewById(R.id.wr_info_hs1);
        wr_info_hash2 = (TextView)findViewById(R.id.wr_info_hs2);
        wr_info_hash3 = (TextView)findViewById(R.id.wr_info_hs3);
        wr_info_houseImage = (ImageView)findViewById(R.id.wr_info_houseImage);
        wr_info_profile = (CircleImageView)findViewById(R.id.wishRoom_info_profile);
        wr_info_reply_icon = (ImageView)findViewById(R.id.wr_info_reply_icon);
        wr_info_scrap_icon = (ImageView)findViewById(R.id.wr_info_scrap_icon);
        wr_info_reply_text = (TextView)findViewById(R.id.wr_info_reply);
        wr_info_scrap_text = (TextView)findViewById(R.id.wr_info_scrap);

        rv = (RecyclerView)findViewById(R.id.wr_info_RecyclerView);
        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WishRoom_info.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);

        Intent intent2 = new Intent();
        setResult(ForRoomConstant.WISHROOM_RESULT_CODE, intent2);
        if(scrapCheck){
            wr_info_scrap_icon.setImageResource(R.drawable.like_select);
        }else{
            wr_info_scrap_icon.setImageResource(R.drawable.like);
        }

        putCommentIntnet[0] = "wr";
        putCommentIntnet[1] = id;

        scraplinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncScrapChangeJSONList().execute(ForRoomApplication.USER_ID);
            }
        });
        replylinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), CommentActivity.class);
                intent.putExtra("toComment",putCommentIntnet);
                startActivityForResult(intent, 867);
            }
        });
//        wr_info_scrap_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AsyncScrapChangeJSONList().execute(ForRoomApplication.USER_ID);
//            }
//        });
//        wr_info_reply_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ForRoomApplication.getFRContext(), CommentActivity.class);
//                intent.putExtra("toComment",putCommentIntnet);
//                startActivityForResult(intent, 867);
//            }
//        });
        new AsyncWishRoominfoJSONList().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("ssong resyltcode", resultCode+"");
        if(resultCode ==ForRoomConstant.WISHROOM_INFO_GOOD_ITEM_RESULT_CODE){
            new AsyncWishRoominfoJSONList().execute();
        }
        if(resultCode == ForRoomConstant.WISHROOM_COMMENT_BACK_RFESULT_CODe){
            new AsyncWishRoominfoJSONList().execute();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(ForRoomConstant.WISHROOM_INFO_BACK_FINISH_RESULT_CODE, intent);
        finish();
    }

    public class AsyncWishRoominfoJSONList extends AsyncTask<String, Integer, WishRoomInfoValueObject>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected WishRoomInfoValueObject doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            WishRoomInfoValueObject WRIList = null;
            try{
                URL target = new URL(ForRoomConstant.TARGET_URL+ForRoomConstant.WISHROOM_READ_PATH+id);
                conn = (HttpURLConnection)target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);

                int resCode = conn.getResponseCode();
                if(resCode == HttpURLConnection.HTTP_OK){
                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = null;
                    StringBuilder jsonBuf = new StringBuilder();
                    while((line = fromServer.readLine())!=null){
                        jsonBuf.append(line);
                    }

//                    Log.e("ssong", jsonBuf+"");

                    WRIList = ParseDataParseHandler.getJSONWishRoomInfoRequestAllList(jsonBuf);
                }

            }catch(Exception e){
                Log.e("AsyncTask in WRI", "AsyncTask 서버 오류");
            }finally {
                if(fromServer != null){
                    try{
                        fromServer.close();
                    }catch(Exception e){

                    }
                    conn.disconnect();
                }
            }

            return WRIList;
        }

        @Override
        protected void onPostExecute(WishRoomInfoValueObject wishRoomInfoValueObjects) {
            wishroomRecyclerAdapter = new WishRoomInfoRecyclerAdapter(WishRoom_info.this, wishRoomInfoValueObjects);
            rv.setAdapter(wishroomRecyclerAdapter);
            wr_info_scrap_text.setText(wishRoomInfoValueObjects.wr_info_scrapcount);
            wr_info_reply_text.setText(wishRoomInfoValueObjects.wr_info_replycount);
            wr_info_share_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ForRoomApplication.getFRContext(), "준비중입니다", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private  class WishRoomInfoRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private static final int TYPE_1=0;
        private static final int TYPE_2=1;
        private Context context;
//        private WishRoomInfoValueObject WRIVO;

        public WishRoomInfoRecyclerAdapter(Context context, WishRoomInfoValueObject WRIVO2){
            this.context = context;
            WRIVO = WRIVO2;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          //  View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishroom_info_top,parent,false);
         //   View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishroom_info, parent, false);
            switch (viewType){
                case TYPE_1 :
                    View v1= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishroom_info_top,parent,false);
                    return new ViewHolder1(v1);
                case TYPE_2:
                    View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishroom_info, parent , false);
                    return new ViewHolder2(v2);
            }
            return null;
        }

        @Override
        public int getItemViewType(int position) {

            if(position == 0){
                return TYPE_1;
            }
            return TYPE_2;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            switch(getItemViewType(position)){
                case TYPE_1:
                    ViewHolder1 holder1 = (ViewHolder1)holder;
                    holder1.wr_info_title.setText(WRIVO.wr_info_title);
                    holder1.wr_info_nick.setText(WRIVO.wr_info_nickname+"'s style");
                    for(int i=0; i<WRIVO.wr_info_hash.length;i++){
                        switch(i){
                            case 0:
                                holder1.wr_info_hash1.setText("#"+WRIVO.wr_info_hash[i]);
                            case 1:
                                holder1.wr_info_hash2.setText("#"+WRIVO.wr_info_hash[i]);
                            case 2:
                                holder1.wr_info_hash3.setText("#"+WRIVO.wr_info_hash[i]);
                        }
                    }
                    Glide.with(context).load(ForRoomConstant.TARGET_URL + WRIVO.wr_info_houseImage).into(holder1.wr_info_wishroomhouse);
                    Glide.with(context).load(ForRoomConstant.TARGET_URL+WRIVO.wr_info_profile).error(R.drawable.profile).skipMemoryCache(true).into(holder1.wr_info_profile);
                    holder1.wr_info_profile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent intent = new Intent(WishRoom_info.this, ProfileActivity.class);
//                            startActivity(intent);
//                            Toast.makeText(ForRoomApplication.getFRContext(), "ㅣ비중입니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(WishRoom_info.this, ProfileActivity.class);
                            intent.putExtra("profiletype", "wishroom");
                            intent.putExtra("userid", WRIVO.wr_info_userid);
                            startActivity(intent);
                        }
                    });
                    break;
                case TYPE_2:
                    ViewHolder2 holder2 = (ViewHolder2)holder;
                    holder2.goods_name.setText(WRIVO.wr_info_gd_name[position-1]);
                    holder2.goods_series.setText(WRIVO.wr_info_gd_series[position-1]);
                    holder2.goods_price.setText(WRIVO.wr_info_gd_price[position - 1]);
                    holder2.goods_scrapcount.setText(WRIVO.wr_info_gd_scrapcount[position-1]);

                    holder2.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(WishRoom_info.this, GoodsItemsActivity.class);
                            intent.putExtra("togooditemtype","fromwishroominfo");
                            intent.putExtra("togooditemid", WRIVO.wr_info_gd_id[position-1]);
                            startActivityForResult(intent, ForRoomConstant.WISHROOM_INFO_GOOD_ITEM_RESULT_CODE);
                        }
                    });
                    Glide.with(context).load(getFilesDir() + "/" + WRIVO.wr_info_gd_id[position -1]+".png").into(holder2.goods_image);
                    break;
            }
        }
        @Override
        public int getItemCount() {
//            Log.e("ssongItemCount",""+WRIVO.wr_info_gd_name.length);
            return (WRIVO.wr_info_gd_name.length+1);
        }

        public  class ViewHolder1 extends RecyclerView.ViewHolder{
            public View mview;
            public TextView wr_info_title, wr_info_nick, wr_info_hash1, wr_info_hash2, wr_info_hash3;
            public ImageView wr_info_wishroomhouse;
            public CircleImageView wr_info_profile;

            public ViewHolder1(View v){
                super(v);
//                Log.e("ViewHolder", "ViewHolder()");
                mview = v;
                wr_info_title = (TextView)v.findViewById(R.id.wr_info_title);
                wr_info_nick = (TextView)v.findViewById(R.id.wr_info_nick);
                wr_info_hash1 = (TextView)v.findViewById(R.id.wr_info_hs1);
                wr_info_hash2 = (TextView)v.findViewById(R.id.wr_info_hs2);
                wr_info_hash3 = (TextView)v.findViewById(R.id.wr_info_hs3);
                wr_info_wishroomhouse = (ImageView)v.findViewById(R.id.wr_info_houseImage);
                wr_info_profile = (CircleImageView)v.findViewById(R.id.wishRoom_info_profile);
            }
        }

        public class ViewHolder2 extends RecyclerView.ViewHolder{
            public ImageView goods_image, goods_scrapicon;
            public TextView goods_name, goods_series, goods_price, goods_scrapcount;
            public View mview;

            public ViewHolder2(View v){
                super(v);
                mview = v;
                goods_image = (ImageView)v.findViewById(R.id.wr_info_goods_image);
                goods_scrapicon = (ImageView)v.findViewById(R.id.mr_info_scrap_icon);
                goods_name = (TextView)v.findViewById(R.id.wr_info_goods_name);
                goods_series = (TextView)v.findViewById(R.id.wr_info_goods_series);
                goods_price = (TextView)v.findViewById(R.id.wr_info_goods_price);
                goods_scrapcount = (TextView)v.findViewById(R.id.wr_info_goods_scarap_count);
            }
        }
    }

    private class AsyncScrapChangeJSONList extends AsyncTask<String, Integer, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result ="";
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            StringBuilder queryBuf = new StringBuilder();

            try{
                queryBuf.append("usrid="+params[0])
                        .append("&docid=" + id);

                URL target = new URL(ForRoomConstant.TARGET_URL+ForRoomConstant.USER_SCRAP_CHANGE+"wr");
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
                Log.e("ssong error", "asynctask scrapchange", e);
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
                if(s.equalsIgnoreCase("INSERT_SCRAP")){
                    wr_info_scrap_icon.setImageResource(R.drawable.like_select);
                    new AsyncWishRoominfoJSONList().execute();
                }else if(s.equalsIgnoreCase("DELETE_SCRAP")){
                    wr_info_scrap_icon.setImageResource(R.drawable.like);
                    new AsyncWishRoominfoJSONList().execute();
                }
            }else{
                Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
