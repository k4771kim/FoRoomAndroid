package com.forroom.suhyemin.kimbogyun.songmin.profilePage;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.forroom.suhyemin.kimbogyun.songmin.ForRoomApplication;
import com.forroom.suhyemin.kimbogyun.songmin.GoodsItemsActivity;
import com.forroom.suhyemin.kimbogyun.songmin.ParseDataParseHandler;
import com.forroom.suhyemin.kimbogyun.songmin.R;
import com.forroom.suhyemin.kimbogyun.songmin.WishRoomWriteActivity;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.GoodsItemInfoValueObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileJjimFragment extends Fragment {
    private RecyclerView rv;
    static ProfileActivity owner;


    public ProfileJjimFragment() {
        // Required empty public constructor
    }

    public static ProfileJjimFragment createInstance() {
        final ProfileJjimFragment pageFragment = new ProfileJjimFragment();
        final Bundle bundle = new Bundle();
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_jjim, container, false);
        owner = (ProfileActivity)getActivity();
        rv = (RecyclerView)v.findViewById(R.id.profile_jjim_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(ForRoomApplication.getFRContext()));

        new AsyncTaskJjimGoods().execute();

        return v;
    }

    private class AsyncTaskJjimGoods extends AsyncTask<String, Integer, ArrayList<GoodsItemInfoValueObject>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<GoodsItemInfoValueObject> doInBackground(String... params) {
            String result ="";
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            StringBuilder queryBuf = new StringBuilder();
            ArrayList<GoodsItemInfoValueObject> goodsVO = null;

            try {
                queryBuf.append("usrid=" + ProfileActivity.userid);

                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.USER_SCRAP_LIST + "gd");
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
//                    Log.e("ssong", jsonBuf + "");
                    goodsVO = ParseDataParseHandler.getGoodsJjimItem(jsonBuf);
                }
            } catch (Exception e) {
//                Log.e("ssong error", "asynctask scrapchange", e);
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
            return goodsVO;
        }
        @Override
        protected void onPostExecute(ArrayList<GoodsItemInfoValueObject> goodsItemInfoValueObjects) {
//            Log.e("ssongsize", goodsItemInfoValueObjects.size()+"");
            rv.setAdapter(new WishRoomRecyclerAdapter(WishRoomWriteActivity.getmContext(), goodsItemInfoValueObjects));
        }
    }

    private static class WishRoomRecyclerAdapter extends RecyclerView.Adapter<WishRoomRecyclerAdapter.ViewHolder>{
        private Context context;
        private List<GoodsItemInfoValueObject> goodsItem;

        public WishRoomRecyclerAdapter(Context context, List<GoodsItemInfoValueObject> list){
            this.context = context;
            this.goodsItem = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_list, parent, false);
            return new ViewHolder(v);
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            GoodsItemInfoValueObject goodsVO = goodsItem.get(position);
            Glide.with(ForRoomApplication.getFRContext()).load(ForRoomConstant.TARGET_URL+goodsVO.goods_image).into(holder.image);
            holder.name.setText(goodsVO.goods_name);
            holder.series.setText(goodsVO.goods_series);
            holder.price.setText(goodsVO.goods_price);
            holder.mview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ForRoomApplication.getFRContext(), GoodsItemsActivity.class);
                    intent.putExtra("togoodsitems", goodsItem.get(position).goods_id);
                    intent.putExtra("togoodsitems2", ForRoomApplication.USER_ID);
                    intent.putExtra("togooditemtype", "fromProfileJjim");
                    owner.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return goodsItem.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public View mview;
            public TextView price, name, series;
            public ImageView image;

            public ViewHolder(View v){
                super(v);
                mview = v;
                price = (TextView)v.findViewById(R.id.goods_list_price);
                name = (TextView)v.findViewById(R.id.goods_list_name);
                series = (TextView)v.findViewById(R.id.goods_list_series);
                image = (ImageView)v.findViewById(R.id.goods_list_image);
            }
        }
    }

}
