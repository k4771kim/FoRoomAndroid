package com.forroom.suhyemin.kimbogyun.songmin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.common.LabelTag;
import com.forroom.suhyemin.kimbogyun.songmin.profilePage.ProfileActivity;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MyRoomInfoValueObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyRoom_info extends AppCompatActivity {
    private String id;
    private boolean scrap_scheck;
    private String[] putCommentIntent = new String[2];
    private TextView mr_info_title, mr_info_nick, mr_info_hash1,
            mr_info_hash2, mr_info_hash3, mr_info_date, mr_info_reply_text, mr_info_scrap_text;
    private CircleImageView mr_info_profile;
    private ImageView mr_info_reply_icon, mr_info_scrap_icon, mr_info_share_icon;
    private RecyclerView rv;
    private ImageView mr_info_leftIcon, mr_info_menuIcon;
    public static int height;
    public static int width;
    public static boolean[] positionCheck;
    private LinearLayout scraplinear, replylinear;

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_info, popup.getMenu());


        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.menu_modify) {
                    Toast.makeText(MyRoom_info.this, "준비중입니다.", Toast.LENGTH_SHORT).show();
                    Log.d("menu_modify", "TT");
                } else if(id == R.id.menu_delete) {
                    Toast.makeText(MyRoom_info.this, "삭제합니다.", Toast.LENGTH_SHORT).show();
                }


                return true;
            }
        });

        try {
            Field mFieldPopup=popup.getClass().getDeclaredField("mPopup");
            mFieldPopup.setAccessible(true);
            MenuPopupHelper mPopup = (MenuPopupHelper) mFieldPopup.get(popup);
            mPopup.setForceShowIcon(true);
        } catch (Exception e) {
    }

        popup.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ForRoomApplication.setTagContext(MyRoom_info.this);
        setContentView(R.layout.activity_myroom_info);
        mr_info_leftIcon = (ImageView) findViewById(R.id.myroom_info_left_icon);
        mr_info_menuIcon = (ImageView) findViewById(R.id.mr_info_menu_icon);



//        if() {  mr_info_menuIcon.setVisibility(View.INVISIBLE); };



        mr_info_leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        mr_info_menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        Display display = getWindowManager().getDefaultDisplay();
        height = display.getHeight();
        width = display.getWidth();
        Intent intent = getIntent();
        id = intent.getStringExtra("toMyRoomInfo");
//        Log.e("ssong id!!!!!!", id);
        putCommentIntent[0] = "mr";
        putCommentIntent[1] = id;
        positionCheck = new boolean[5];
        scrap_scheck = intent.getBooleanExtra("toMyRoomInfo2", false);

        scraplinear = (LinearLayout)findViewById(R.id.mr_info_scrap_linear);
        replylinear = (LinearLayout)findViewById(R.id.mr_info_reply_linear);
        mr_info_share_icon = (ImageView)findViewById(R.id.mr_info_share_icon);
        mr_info_title = (TextView) findViewById(R.id.mr_info_title_text);
        mr_info_nick = (TextView) findViewById(R.id.mr_info_nick_text);
        mr_info_hash1 = (TextView) findViewById(R.id.mr_info_hash1_text);
        mr_info_hash2 = (TextView) findViewById(R.id.mr_info_hash2_text);
        mr_info_hash3 = (TextView) findViewById(R.id.mr_info_hash3_text);
        mr_info_date = (TextView) findViewById(R.id.mr_info_date_text);
        mr_info_reply_text = (TextView) findViewById(R.id.mr_info_reply_text);
        mr_info_scrap_text = (TextView) findViewById(R.id.mr_info_scrap_text);
        mr_info_profile = (CircleImageView) findViewById(R.id.mr_info_profile);
        mr_info_reply_icon = (ImageView) findViewById(R.id.mr_info_reply_icon);
        mr_info_scrap_icon = (ImageView) findViewById(R.id.mr_info_scrap_icon);

        rv = (RecyclerView) findViewById(R.id.mr_info_recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(MyRoom_info.this));

        new AsyncMyRoominfoJSONList().execute();


        if (scrap_scheck) {
            mr_info_scrap_icon.setImageResource(R.drawable.like_select);
        } else {
            mr_info_scrap_icon.setImageResource(R.drawable.like);
        }

        scraplinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncMyRoomScrapChangeJSONList().execute(ForRoomApplication.USER_ID);
            }
        });
        replylinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForRoomApplication.getFRContext(), CommentActivity.class);
                intent.putExtra("toComment", putCommentIntent);
                startActivityForResult(intent,444);
            }
        });
//        mr_info_scrap_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AsyncMyRoomScrapChangeJSONList().execute(ForRoomApplication.USER_ID);
//            }
//        });
//
//        mr_info_reply_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ForRoomApplication.getFRContext(), CommentActivity.class);
//                intent.putExtra("toComment", putCommentIntent);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode ==ForRoomConstant.MYROOM_COMMENT_BACK_RFESULT_CODE){
            new AsyncMyRoominfoJSONList().execute();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(ForRoomConstant.MYROOM_INFO_BACK_FINISH_RESULT_CODE, intent);
        finish();
    }

    public class AsyncMyRoominfoJSONList extends AsyncTask<String, Integer, MyRoomInfoValueObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MyRoomInfoValueObject doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            MyRoomInfoValueObject MRIVO = null;
            try {
                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.MYROOM_READ_PATH + id);
                conn = (HttpURLConnection) target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);

                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = null;
                    StringBuilder jsonBuf = new StringBuilder();
                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }

//                    Log.e("ssong", jsonBuf.toString());
                    MRIVO = ParseDataParseHandler.getJSONMyRoomInfoRequestAllList(jsonBuf);
                }
            } catch (Exception e) {
                Log.e("get MyRoomInfoJSON", "asynctask 서버 오류", e);
            } finally {
                if (fromServer != null) {
                    try {
                        fromServer.close();
                    } catch (Exception e) {

                    }
                    conn.disconnect();
                }
            }
            return MRIVO;
        }

        @Override
        protected void onPostExecute(MyRoomInfoValueObject myRoomInfoValueObject) {
//            MyRoomInfoValueObject MRIVO = myRoomInfoValueObject;

            MyRoomInfoRecyclerViewAdapter adapter = new MyRoomInfoRecyclerViewAdapter(MyRoom_info.this, myRoomInfoValueObject);

            rv.setAdapter(adapter);
            mr_info_share_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ForRoomApplication.getFRContext(), "준비중입니다", Toast.LENGTH_SHORT);
                }
            });
            mr_info_reply_text.setText(myRoomInfoValueObject.mr_info_reply_text);
            mr_info_scrap_text.setText(myRoomInfoValueObject.mr_info_scrap_text);
        }
    }

    private class MyRoomInfoRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Context context;
        private MyRoomInfoValueObject MRIVO;
        private static final int TYPE_1 = 0;
        private static final int TYPE_2 = 1;
        private RelativeLayout[] RelArray;

        public MyRoomInfoRecyclerViewAdapter(Context context, MyRoomInfoValueObject args) {
            this.context = context;
            this.MRIVO = args;
            RelArray = new RelativeLayout[args.mr_info_myroomimage.length];
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myroom_info_top, parent , false);
//            View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myroom_info, parent, false);
            switch (viewType) {
                case TYPE_1:
                    View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myroom_info_top, parent, false);
                    return new ViewHolder1(v1);
                case TYPE_2:
                    View v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myroom_info, parent, false);
                    return new ViewHolder2(v2);
            }
            return null;

        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return TYPE_1;
            }
            return TYPE_2;
        }

        public final int[] tagresource = {R.drawable.btn_t_wood_01_2, R.drawable.btn_t_white_02_2, R.drawable.btn_t_grey_03_2, R.drawable.btn_t_beige_04_2, R.drawable.btn_t_green_05_2,
                R.drawable.btn_t_violet_06_2, R.drawable.btn_t_red_07_2, R.drawable.btn_t_black_08_2, R.drawable.btn_t_blue_09_2, R.drawable.btn_t_gold_10_2,
                R.drawable.btn_t_brown_11_2, R.drawable.btn_t_pink_12_2};

        ViewHolder2 holder2;

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            switch (getItemViewType(position)) {
                case TYPE_1:
                    ViewHolder1 holder1 = (ViewHolder1) holder;

                    holder1.mr_info_title.setText(MRIVO.mr_info_title);
                    DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
//                    holder1.mr_info_date.setText(dateFormat.format(MRIVO.mr_info_date)+"");
                    holder1.mr_info_date.setText(MRIVO.mr_info_date);
                    holder1.mr_info_nick.setText(MRIVO.mr_info_nickname + "'s style");
                    for (int i = 0; i < MRIVO.mr_info_hash.length; i++) {
                        switch (i) {
                            case 0:
                                holder1.mr_info_hash1.setText("#" + MRIVO.mr_info_hash[i]);
                                break;
                            case 1:
                                holder1.mr_info_hash2.setText("#" + MRIVO.mr_info_hash[i]);
                                break;
                            case 2:
                                holder1.mr_info_hash3.setText("#" + MRIVO.mr_info_hash[i]);
                                break;
                        }
                    }

                    Glide.with(MyRoom_info.this).load(ForRoomConstant.TARGET_URL + MRIVO.mr_info_profile).error(R.drawable.profile).skipMemoryCache(true).into(holder1.mr_info_profile);
                    holder1.mr_info_profile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                           Toast.makeText(ForRoomApplication.getFRContext(), "준비중입니다", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MyRoom_info.this, ProfileActivity.class);
                            intent.putExtra("profiletype", "myroom");
                            intent.putExtra("userid", MRIVO.mr_info_userid);
                            startActivity(intent);
                        }
                    });

                    break;
                case TYPE_2:
                    final int mposition = position;
                    holder2 = (ViewHolder2) holder;
                    RelArray[mposition - 1] = holder2.rel;

//                    Log.e("sg", MRIVO.mr_info_myroomimage[position-1]);
//                    Log.e("sg", MRIVO.mr_info_myroomtext[position-1]);
                    holder2.mr_info_item_text.setText(MRIVO.mr_info_myroomtext[position - 1]);

                    Glide.with(context).load(ForRoomConstant.TARGET_URL+MRIVO.mr_info_myroomimage[position-1]).into(((ViewHolder2) holder).mr_info_item_myRoomImage);

 /*                   Glide.with(context).load(ForRoomConstant.TARGET_URL + MRIVO.mr_info_myroomimage[mposition - 1]).fitCenter().listener(new RequestListener<String, GlideDrawable>() {
                        public int top, bottom;
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            Log.e("프롬 메모리캐시?", isFromMemoryCache + "");
//                            Log.e("퍼스트 리소스??", isFirstResource + "");
                            target.onLoadCleared(resource);
                            int imageHeight = resource.getIntrinsicHeight();


                            set(position, imageHeight);


//                            if (!isFromMemoryCache) {
//                                target.onLoadStarted(resource);
//                                int imageHeight = resource.getIntrinsicHeight();
//                                set(position, imageHeight);
//                            } else {
//                                target.onLoadCleared(resource);
//                            }

//                            Log.e("포지션", mposition - 1 + "");
                            return true;
                        }
                    }).into(holder2.mr_info_item_myRoomImage);*/

                    break;
            }
        }

 /*       public void set(int p , int h) {
            for (int i = 0; i < MRIVO.pictag.get(p - 1).dataX.length; i++) {
//                                Log.e("포지션", mposition-1+"");

                LabelTag LT = new LabelTag(getApplicationContext());

                LT.MoveXY((float) (MRIVO.pictag.get(p - 1).dataX[i] * getWidth()), (float) (MRIVO.pictag.get(p - 1).dataY[i] * h), 1, 2);
                LT.setBackgroundResource(tagresource[MRIVO.pictag.get(p - 1).Color[i] - 1]);
                LT.setText(MRIVO.pictag.get(p - 1).Text[i]);

                LT.movable = false;
                RelArray[p-1].addView(LT);
//                OLD_LabelTagArrayListArray[position].add(LT);
            }
        }*/

        @Override
        public int getItemCount() {
            return (MRIVO.mr_info_myroomimage.length + 1);
        }

        public class ViewHolder1 extends RecyclerView.ViewHolder {
            public TextView mr_info_nick, mr_info_title, mr_info_date, mr_info_hash1,
                    mr_info_hash2, mr_info_hash3, mr_info_replytext, mr_info_scraptext;
            public CircleImageView mr_info_profile;
            public View mview;


            public ViewHolder1(View v) {
                super(v);
                mview = v;
                mr_info_nick = (TextView) v.findViewById(R.id.mr_info_nick_text);
                mr_info_title = (TextView) v.findViewById(R.id.mr_info_title_text);
                mr_info_date = (TextView) v.findViewById(R.id.mr_info_date_text);
                mr_info_hash1 = (TextView) v.findViewById(R.id.mr_info_hash1_text);
                mr_info_hash2 = (TextView) v.findViewById(R.id.mr_info_hash2_text);
                mr_info_hash3 = (TextView) v.findViewById(R.id.mr_info_hash3_text);
                mr_info_replytext = (TextView) v.findViewById(R.id.mr_info_reply_text);
                mr_info_scraptext = (TextView) v.findViewById(R.id.mr_info_scrap_text);

                mr_info_profile = (CircleImageView) v.findViewById(R.id.mr_info_profile);

            }
        }

        public class ViewHolder2 extends RecyclerView.ViewHolder {
            public View mview;
            public TextView mr_info_item_text;
            public ImageView mr_info_item_myRoomImage;
            public RelativeLayout rel;

            public ViewHolder2(View v) {
                super(v);
                rel = (RelativeLayout) v.findViewById(R.id.mr_info_relative);
                mr_info_item_text = (TextView) v.findViewById(R.id.mr_info_item_text);
                mr_info_item_myRoomImage = (ImageView) v.findViewById(R.id.mr_info_item_image);
            }
        }
    }

    private class AsyncMyRoomScrapChangeJSONList extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            StringBuilder queryBuf = new StringBuilder();

            try {
                queryBuf.append("usrid=" + params[0])
                        .append("&docid=" + id);

                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.USER_SCRAP_CHANGE + "mr");
                conn = (HttpURLConnection) target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                OutputStream toServer = conn.getOutputStream();
                toServer.write(new String(queryBuf.toString()).getBytes("UTF-8"));
                toServer.close();

                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String onLine = "";

                    StringBuilder jsonBuf = new StringBuilder();
                    while ((onLine = fromServer.readLine()) != null) {
                        jsonBuf.append(onLine);
                    }
                    JSONObject responseData = new JSONObject(jsonBuf.toString());
                    result = responseData.getString("result");
                }
            } catch (Exception e) {
                Log.e("ssong error", "asynctask scrapchange", e);
            } finally {
                if (fromServer != null) {
                    try {
                        fromServer.close();
                    } catch (Exception e) {
                    }
                    if (conn != null) {
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
            if (s != null) {
                if (s.equalsIgnoreCase("INSERT_SCRAP")) {
                    mr_info_scrap_icon.setImageResource(R.drawable.like_select);
                    new AsyncMyRoominfoJSONList().execute();
                } else if (s.equalsIgnoreCase("DELETE_SCRAP")) {
                    mr_info_scrap_icon.setImageResource(R.drawable.like);
                    new AsyncMyRoominfoJSONList().execute();
                }
            } else {
                Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }



}
