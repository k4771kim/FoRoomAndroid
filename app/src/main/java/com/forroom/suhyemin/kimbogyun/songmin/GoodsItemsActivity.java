package com.forroom.suhyemin.kimbogyun.songmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.GoodsItemInfoValueObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GoodsItemsActivity extends AppCompatActivity {
    ImageView goods_item_image, goods_item_jjimicon, goods_item_buypage, goods_item_ok_icon;
    TextView goods_item_name, goods_item_desc, goods_item_jjimcount, goods_item_price;
    String goodsId, userId;
    private ImageView left_back;
    private String type;
    String goods_id;
    int seriesno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_items);

        left_back = (ImageView) findViewById(R.id.goods_item_left_back);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();
        seriesno = intent.getIntExtra("toseriesnum", 0);
        userId = intent.getStringExtra("togoodsitems2");
        type = intent.getStringExtra("togooditemtype");
        goods_item_name = (TextView) findViewById(R.id.goods_item_name);
        goods_item_desc = (TextView) findViewById(R.id.goods_item_desc);
        goods_item_jjimcount = (TextView) findViewById(R.id.goods_item_jjimcount);
        goods_item_price = (TextView) findViewById(R.id.goods_item_price);

        goods_item_image = (ImageView) findViewById(R.id.goods_item_image);
        goods_item_jjimicon = (ImageView) findViewById(R.id.goods_item_jjimicon);
        goods_item_buypage = (ImageView) findViewById(R.id.goods_item_buypageimage);
        goods_item_ok_icon = (ImageView) findViewById(R.id.goods_item_ok_icon);
        if (type.equalsIgnoreCase("fromwishroominfo")) {
            goods_item_ok_icon.setVisibility(View.INVISIBLE);
            goodsId = intent.getStringExtra("togooditemid");
        } else if (type.equalsIgnoreCase("fromgooditemlist")) {
            goodsId = intent.getStringExtra("togoodsitems");
        } else {
            goodsId = intent.getStringExtra("togoodsitems");
            goods_item_ok_icon.setVisibility(View.INVISIBLE);
        }


        goods_item_jjimicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTaskScrapChangeJSONList().execute(ForRoomApplication.USER_ID);
            }
        });

        new AsyncTaskGoodsItemJSONList().execute();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(ForRoomConstant.WISHROOM_INFO_GOOD_ITEM_RESULT_CODE, intent);
        finish();
    }

    private class AsyncTaskGoodsItemJSONList extends AsyncTask<String, Integer, GoodsItemInfoValueObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected GoodsItemInfoValueObject doInBackground(String... params) {
            String result = "";
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            StringBuilder queryBuf = new StringBuilder();
            GoodsItemInfoValueObject goodsinfoVO = null;

            try {
                queryBuf.append("usrid=" + ForRoomApplication.USER_ID)
                        .append("&docid=" + goodsId);

                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.GOODS_INFO_PATH);
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
                    goodsinfoVO = ParseDataParseHandler.getGoodsItem(jsonBuf);
                }
            } catch (Exception e) {
                Log.e("ssong error", "asynctask goods", e);
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
            return goodsinfoVO;
        }


        @Override
        protected void onPostExecute(GoodsItemInfoValueObject s) {
            super.onPostExecute(s);
            goods_id = s.goods_id;
            goods_item_name.setText(s.goods_name);
            goods_item_price.setText(s.goods_price);
            Glide.with(GoodsItemsActivity.this).load(getFilesDir() + "/" + goods_id + ".png").into(goods_item_image);
            goods_item_desc.setText(s.goods_desc);
            goods_item_jjimcount.setText(s.goods_scrap_count);
            if (s.goods_scrap_check) {
                goods_item_jjimicon.setImageResource(R.drawable.btn_wishbox_on);
            } else {
                goods_item_jjimicon.setImageResource(R.drawable.btn_wishbox_off);
            }
            final String a = s.goods_image;
//            Log.e("ssongaaaaaaaaaaa", a);
            goods_item_ok_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Log.e(getFilesDir() + "/" + goods_id + ".png", "여기에");
                    Bitmap b = BitmapFactory.decodeFile(getFilesDir() + "/" + goods_id + ".png");
                    if (seriesno == 41) {
                        WishRoomWriteActivity.intentarray[4] = goodsId;
                    }

                    if (WishRoomWriteActivity.goods_id.containsKey(goods_id)) {
                        int i = WishRoomWriteActivity.goods_id.get(goods_id);
                        WishRoomWriteActivity.goods_id.put(goods_id, i++);
                    } else {
                        WishRoomWriteActivity.goods_id.put(goods_id, 1);
                    }

                    WishRoomWriteActivity.turboImage.addObject(WishRoomWriteActivity.getmContext(), b);
                    finish();
                }
            });

        }
    }

    private class AsyncTaskScrapChangeJSONList extends AsyncTask<String, Integer, String> {
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
                        .append("&docid=" + goodsId);

                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.USER_SCRAP_CHANGE + "gd");
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
                    goods_item_jjimicon.setImageResource(R.drawable.btn_wishbox_on);
                    new AsyncTaskGoodsItemJSONList().execute();
                } else if (s.equalsIgnoreCase("DELETE_SCRAP")) {
                    goods_item_jjimicon.setImageResource(R.drawable.btn_wishbox_off);
                    new AsyncTaskGoodsItemJSONList().execute();
                }
            } else {
                Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
