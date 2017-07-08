package com.forroom.suhyemin.kimbogyun.songmin.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.forroom.suhyemin.kimbogyun.songmin.ForRoomApplication;
import com.forroom.suhyemin.kimbogyun.songmin.MainActivity;
import com.forroom.suhyemin.kimbogyun.songmin.ParseDataParseHandler;
import com.forroom.suhyemin.kimbogyun.songmin.R;
import com.forroom.suhyemin.kimbogyun.songmin.WishRoom_info;
import com.forroom.suhyemin.kimbogyun.songmin.common.EndlessScrollRecyclListener;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.WishRoomValueObject;

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
public class WishRoomFragment extends Fragment {
    static MainActivity owner;
    public static RecyclerView rv;
    public static WishRoomRecyclerViewAdapter wishRoomRecyclerAdapter;


    public WishRoomFragment() {
    }

    public static WishRoomFragment createInstance() {
        final WishRoomFragment pageFragment = new WishRoomFragment();
        final Bundle bundle = new Bundle();
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    public static ImageButton iv;
    public static boolean search = true;// true : 최신순/ false : 인기순
    private static String rankList;
    //    private int page;
    static boolean morePage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wish_room, container, false);
        morePage = true;
        wishRoomRecyclerAdapter = new WishRoomRecyclerViewAdapter(ForRoomApplication.getFRContext());
        owner = (MainActivity) getActivity();
        iv = (ImageButton) v.findViewById(R.id.wishroom_pp);

        if (!search)
            iv.setImageResource(R.drawable.array_select_best);
        else
            iv.setImageResource(R.drawable.array_select_new);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search) {
                    //인기순으로 변경
                    rv.setAdapter(null);
                    search = false;
                    morePage = true;
                    iv.setImageResource(R.drawable.array_select_best);
                    rankList = "&rank=12";
                    new AsyncWishRoomJSONList().execute(ForRoomApplication.USER_ID, "1", rankList);

                } else {
                    //최신순으로 변경
                    rv.setAdapter(null);
                    search = true;
                    morePage = true;
                    iv.setImageResource(R.drawable.array_select_new);
                    rankList = "";
                    new AsyncWishRoomJSONList().execute(ForRoomApplication.USER_ID, "1", rankList);

                }

            }
        });


        rv = (RecyclerView) v.findViewById(R.id.wishroom_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(ForRoomApplication.getFRContext()));

        new AsyncWishRoomJSONList().execute(ForRoomApplication.USER_ID, "1", rankList);

//        rv.addOnScrollListener(new EndlessScrollRecyclListener() {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                if (morePage)
//                    new AsyncWishRoomJSONList().execute(ForRoomApplication.USER_ID, page + "", rankList);
//            }
//        });
        return v;
    }


    public static class AsyncWishRoomJSONList extends AsyncTask<String, Integer, ArrayList<WishRoomValueObject>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<WishRoomValueObject> doInBackground(String... params) {
            String requestQuery = "usrid=" + params[0] + params[2];
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            ArrayList<WishRoomValueObject> wishList = null;
            try {
                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.WISHROOM_LIST_PATH + params[1]);
                conn = (HttpURLConnection) target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                OutputStream toServer = conn.getOutputStream();
                toServer.write(requestQuery.getBytes("UTF-8"));
                toServer.close();
                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = null;
                    StringBuilder jsonBuf = new StringBuilder();
                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
//                        Log.e("while", line);
                    }
//                    Log.e("데이터",jsonBuf.toString());
                    wishList = ParseDataParseHandler.getJSONWishRoomRequestAllList(jsonBuf);
                }

            } catch (Exception e) {
                Log.i("Asynctaskwishroom", "dd", e);

            } finally {
                if (fromServer != null) {
                    try {
//                        Log.e("server", "server.close");
                        fromServer.close();
                    } catch (Exception e) {
                        Log.i("asynctaskwishroom", "server not", e);
                    }
                    conn.disconnect();
//                    Log.e("server", "serverdisconnect");
                }
            }
//            Log.e("server", "return");
            return wishList;

        }

        @Override
        protected void onPostExecute(ArrayList<WishRoomValueObject> wishRoomValueObjects) {
            if (wishRoomValueObjects != null && wishRoomValueObjects.size() > 0) {
//                    Log.e("ssong", "wwwww");
                if (rv.getAdapter() == null) {
                    wishRoomRecyclerAdapter.setListItem(wishRoomValueObjects);
                    rv.setAdapter(wishRoomRecyclerAdapter);
                    rv.addOnScrollListener(new EndlessScrollRecyclListener() {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount) {
                            if (morePage)
                                new AsyncWishRoomJSONList().execute(ForRoomApplication.USER_ID, page + "", rankList);
                        }
                    });
                } else {
                    wishRoomRecyclerAdapter.addListItem(wishRoomValueObjects);
                }
            } else {
                morePage = false;
            }
        }
    }

    //    public class AsyncWishRoomRankJSONList extends AsyncTask<String, Integer, ArrayList<WishRoomValueObject>>{
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//        @Override
//        protected ArrayList<WishRoomValueObject> doInBackground(String... params) {
//            String requestQuery = "usrid="+params[0]+" &rank=12";
//            HttpURLConnection conn = null;
//            BufferedReader fromServer = null;
//            ArrayList<WishRoomValueObject> wishList = null;
//            try{
//                URL target = new URL(ForRoomConstant.TARGET_URL+ForRoomConstant.WISHROOM_LIST_PATH);
//                conn = (HttpURLConnection)target.openConnection();
//                conn.setConnectTimeout(10000);
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//                conn.setRequestMethod("POST");
//                OutputStream toServer = conn.getOutputStream();
//                toServer.write(requestQuery.getBytes("UTF-8"));
//                toServer.close();
//                int resCode = conn.getResponseCode();
//                if(resCode == HttpURLConnection.HTTP_OK){
//                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//                    String line = null;
//                    StringBuilder jsonBuf = new StringBuilder();
//                    while((line = fromServer.readLine())!=null){
//                        jsonBuf.append(line);
////                        Log.e("while", line);
//                    }
////                    Log.e("데이터",jsonBuf.toString());
//                    wishList = ParseDataParseHandler.getJSONWishRoomRequestAllList(jsonBuf);
//                }
//
//            }catch(Exception e){
////                Log.i("Asynctaskwishroom", "dd", e);
//
//            }finally {
//                if(fromServer != null){
//                    try {
////                        Log.e("server", "server.close");
//                        fromServer.close();
//                    }catch(Exception e){
//                        Log.i("asynctaskwishroom", "server not", e);
//                    }
//                    conn.disconnect();
//                    Log.e("server", "serverdisconnect");
//                }
//            }
////            Log.e("server", "return");
//            return wishList;
//
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<WishRoomValueObject> wishRoomValueObjects) {
//            if(wishRoomValueObjects != null && wishRoomValueObjects.size()>0) {
////                Log.e("ssong", "wwwww");
//                wishRoomRecyclerAdapter = new WishRoomRecyclerViewAdapter(ForRoomApplication.getFRContext(), wishRoomValueObjects);
//                rv.setAdapter(wishRoomRecyclerAdapter);
//            }
//        }
//    }
    public static class WishRoomRecyclerViewAdapter extends RecyclerView.Adapter<WishRoomRecyclerViewAdapter.ViewHolder> {
        private Context context;
        private List<WishRoomValueObject> wishRoomItem;


        public WishRoomRecyclerViewAdapter(Context context) {
            this.context = context;
        }

        public void setListItem(List<WishRoomValueObject> WVO) {
            this.wishRoomItem = WVO;
        }


        public void addListItem(List<WishRoomValueObject> WVO) {
            this.wishRoomItem.addAll(WVO);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wishroom, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final WishRoomValueObject wishData = wishRoomItem.get(position);

            holder.wr_title.setText(wishData.title);
            holder.wr_nick.setText(wishData.nickname + "'s style");


//            Log.e("ssongtest hashle   +"+position , wishData.hash.length+"");
            for (int i = 0; i < wishRoomItem.get(position).hash.length; i++) {
                if (i == 0) {
                    holder.wr_hs2.setText("");
                    holder.wr_hs3.setText("");
                    holder.wr_hs1.setText("#" + wishRoomItem.get(position).hash[i]);
                }
                if (i == 1) {
                    holder.wr_hs2.setText("");
                    holder.wr_hs3.setText("");
                    holder.wr_hs2.setText("#" + wishRoomItem.get(position).hash[i]);
                }
                if (i == 2) {
                    holder.wr_hs3.setText("#" + wishRoomItem.get(position).hash[i]);
                }
            }
            holder.wr_comment.setText(wishData.comment_text);
            holder.wr_like.setText(wishData.like_text);
//            Log.e("position" + position, wishData.wishhouse);
            Glide.with(context).load(ForRoomConstant.TARGET_URL + wishData.wishhouse).placeholder(R.drawable.loading_wishroom).into(holder.wr_house);

            if (wishData.wr_scrap_check) {
                holder.wr_scrap_icon.setImageResource(R.drawable.like_select);
            } else {
                holder.wr_scrap_icon.setImageResource(R.drawable.like);
            }

            holder.mview.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ForRoomApplication.getFRContext(), WishRoom_info.class);

                    intent.putExtra("toWishRoomInfo2", wishData.wr_scrap_check);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("toWishRoomInfo", wishData.id);
                    owner.startActivityForResult(intent, 1);
                }
            });
        }

        @Override
        public int getItemCount() {
            return wishRoomItem.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView wr_title, wr_nick, wr_hs1, wr_hs2, wr_hs3, wr_like, wr_comment;
            public ImageView wr_house, wr_scrap_icon;
            public View mview;
            public ViewHolder(View view) {
                super(view);
                mview = view;
                wr_title = (TextView) view.findViewById(R.id.wishroom_title);
                wr_nick = (TextView) view.findViewById(R.id.wishroom_nick);
                wr_hs1 = (TextView) view.findViewById(R.id.wishroom_hs1);
                wr_hs2 = (TextView) view.findViewById(R.id.wishroom_hs2);
                wr_hs3 = (TextView) view.findViewById(R.id.wishroom_hs3);
                wr_like = (TextView) view.findViewById(R.id.wishroom_like_text);
                wr_comment = (TextView) view.findViewById(R.id.wishroom_comment_text);
                wr_scrap_icon = (ImageView) view.findViewById(R.id.wishroom_like);
                wr_house = (ImageView) view.findViewById(R.id.wishroom_houseline);
            }
        }
    }
    public void recyclerviewinit(ArrayList<WishRoomValueObject> wvo) {
        WishRoomRecyclerViewAdapter adapter = new WishRoomRecyclerViewAdapter(ForRoomApplication.getFRContext());
        adapter.setListItem(wvo);
        rv.setAdapter(adapter);

    }


}
