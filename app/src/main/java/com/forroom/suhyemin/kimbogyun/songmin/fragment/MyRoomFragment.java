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
import com.forroom.suhyemin.kimbogyun.songmin.MyRoom_info;
import com.forroom.suhyemin.kimbogyun.songmin.ParseDataParseHandler;
import com.forroom.suhyemin.kimbogyun.songmin.R;
import com.forroom.suhyemin.kimbogyun.songmin.common.EndlessScrollRecyclListener;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MyRoomValueObject;
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
public class MyRoomFragment extends Fragment {
    static MainActivity owner;
    public static RecyclerView rv;
    public static MyRoomRecyclerViewAdapter myRoomRecyclerViewAdapter;

    public static MyRoomFragment createInstance() {
        final MyRoomFragment pageFragment = new MyRoomFragment();
        final Bundle bundle = new Bundle();
        pageFragment.setArguments(bundle);

        return pageFragment;
    }


    public static ImageButton iv;
    public static boolean search = true;// true : 최신순/ false : 인기순
    private static String rankList;
    static boolean morePage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_room, container, false);
        morePage = true;
        myRoomRecyclerViewAdapter = new MyRoomRecyclerViewAdapter(ForRoomApplication.getFRContext());

        owner = (MainActivity) getActivity();
        iv = (ImageButton) v.findViewById(R.id.myroom_pp);
        if (!search)
            iv.setImageResource(R.drawable.array_select_best);
        else
            iv.setImageResource(R.drawable.array_select_new);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRoomRecyclerViewAdapter = new MyRoomRecyclerViewAdapter(ForRoomApplication.getFRContext());
                if (search) {
                    //인기순으로 변경
                    rv.setAdapter(null);
                    search = false;
                    morePage = true;
                    iv.setImageResource(R.drawable.array_select_best);
                    rankList = "&rank=12";
                    new AsyncTaskMyRoomJSONList().execute(ForRoomApplication.USER_ID, "1", rankList);

                } else {
                    //최신순으로 변경
                    rv.setAdapter(null);
                    search = true;
                    morePage = true;
                    iv.setImageResource(R.drawable.array_select_new);
                    rankList = "";
                    new AsyncTaskMyRoomJSONList().execute(ForRoomApplication.USER_ID, "1", rankList);

                }

//                rv.addOnScrollListener(new EndlessScrollRecyclListener() {
//                    @Override
//                    public void onLoadMore(int page, int totalItemsCount) {
//                        if (morePage) {
//                            new AsyncTaskMyRoomJSONList().execute(ForRoomApplication.USER_ID, page + "", rankList);
//                        }
//                    }
//                });
            }
        });

        rv = (RecyclerView) v.findViewById(R.id.myroom_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(ForRoomApplication.getFRContext()));
        new AsyncTaskMyRoomJSONList().execute(ForRoomApplication.USER_ID, "1", rankList);
//        rv.addOnScrollListener(new EndlessScrollRecyclListener() {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                if (morePage) {
//                    new AsyncTaskMyRoomJSONList().execute(ForRoomApplication.USER_ID, page + "", rankList);
//                }
//            }
//        });




        return v;
    }

    public static class AsyncTaskMyRoomJSONList extends AsyncTask<String, Integer, ArrayList<MyRoomValueObject>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<MyRoomValueObject> doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            ArrayList<MyRoomValueObject> myRoomList = null;
            String requestQuery = "usrid=" + params[0] + params[2];
            try {
                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.MYROOM_LIST_PATH + params[1]);
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
                    }
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
        protected void onPostExecute(ArrayList<MyRoomValueObject> myRoomValueObjects) {
            if (myRoomValueObjects != null && myRoomValueObjects.size() > 0) {

                if (rv.getAdapter() == null) {
                    myRoomRecyclerViewAdapter.setListItem(myRoomValueObjects);
                    rv.setAdapter(myRoomRecyclerViewAdapter);
                    rv.addOnScrollListener(new EndlessScrollRecyclListener() {
                        @Override
                        public void onLoadMore(int page, int totalItemsCount) {
                            if (morePage) {
                                new AsyncTaskMyRoomJSONList().execute(ForRoomApplication.USER_ID, page + "", rankList);
                            }
                        }
                    });
                } else {
                    myRoomRecyclerViewAdapter.addListItem(myRoomValueObjects);
                }
            } else {
                morePage = false;
            }

        }
    }

    public static class MyRoomRecyclerViewAdapter extends RecyclerView.Adapter<MyRoomRecyclerViewAdapter.ViewHolder> {
        private Context context;
        private List<MyRoomValueObject> myRoomItem;


        public MyRoomRecyclerViewAdapter(Context context) {
//            Log.e("ssong", "마이룸 생성자");
            this.context = context;

        }

        public void setListItem(List<MyRoomValueObject> mvo) {
            this.myRoomItem = mvo;
        }

        public void addListItem(List<MyRoomValueObject> mvo) {
            this.myRoomItem.addAll(mvo);
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            Log.e("ssong", "마이룸 create");
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myroom, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
//            Log.e("ssong", "마이룸 onBindView");
            final MyRoomValueObject MVO = myRoomItem.get(position);

            holder.mr_title.setText(MVO.title);
            holder.mr_nickname.setText(MVO.nickname + "'s style");
//            Log.e("ssong test    "+position, MVO.hash.length+"");

            for (int i = 0; i < MVO.hash.length; i++) {
                if (i == 0) {
                    holder.mr_hs2.setText("");
                    holder.mr_hs3.setText("");
                    holder.mr_hs4.setText("");
                    holder.mr_hs5.setText("");
                    holder.mr_hs1.setText("#" + MVO.hash[i]);
                }

                if (i == 1) {
                    holder.mr_hs2.setText("");
                    holder.mr_hs3.setText("");
                    holder.mr_hs4.setText("");
                    holder.mr_hs5.setText("");
                    holder.mr_hs2.setText("#" + MVO.hash[i]);
                }

                if (i == 2) {
                    holder.mr_hs3.setText("");
                    holder.mr_hs4.setText("");
                    holder.mr_hs5.setText("");
                    holder.mr_hs3.setText("#" + MVO.hash[i]);
                }

                if (i == 3) {
                    holder.mr_hs4.setText("");
                    holder.mr_hs5.setText("");
                    holder.mr_hs4.setText("#" + MVO.hash[i]);
                }

                if (i == 4) {
                    holder.mr_hs5.setText("");
                    holder.mr_hs5.setText("#" + MVO.hash[i]);
                }

            }

            holder.mr_comment.setText(MVO.comment_text);
            holder.mr_like.setText(MVO.like_text);
            if (MVO.mr_scrap_check) {
                holder.mr_scrap_icon.setImageResource(R.drawable.like_select);
            } else {
                holder.mr_scrap_icon.setImageResource(R.drawable.like);
            }

//            Log.e("ssong image", MVO.myhouse[0]);
            Glide.with(context).load(ForRoomConstant.TARGET_URL + MVO.myhouse[0]).placeholder(R.drawable.loading_myroom).into(holder.mr_myhouse);

            holder.mview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ForRoomApplication.getFRContext(), MyRoom_info.class);
//                    Log.e("MyRoomFragment id", MVO.id);
                    intent.putExtra("toMyRoomInfo", MVO.id);
//                    Log.e("MyRoomFragment scrap_scheck", MVO.mr_scrap_check+"");
                    intent.putExtra("toMyRoomInfo2", MVO.mr_scrap_check);
                    owner.startActivityForResult(intent, 646);
                }
            });
        }

        @Override
        public int getItemCount() {
            return myRoomItem.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mr_title, mr_nickname, mr_hs1, mr_hs2, mr_hs3, mr_hs4, mr_hs5, mr_like, mr_comment;
            public ImageView mr_myhouse, mr_scrap_icon;
            public View mview;

            public ViewHolder(View view) {
                super(view);
                mview = view;

                mr_title = (TextView) view.findViewById(R.id.myroom_title);
                mr_nickname = (TextView) view.findViewById(R.id.myroom_nickname);
                mr_hs1 = (TextView) view.findViewById(R.id.mr_hs1);
                mr_hs2 = (TextView) view.findViewById(R.id.mr_hs2);
                mr_hs3 = (TextView) view.findViewById(R.id.mr_hs3);
                mr_hs4 = (TextView) view.findViewById(R.id.mr_hs4);
                mr_hs5 = (TextView) view.findViewById(R.id.mr_hs5);

                mr_like = (TextView) view.findViewById(R.id.mr_like_text);
                mr_comment = (TextView) view.findViewById(R.id.mr_comment_text);

                mr_scrap_icon = (ImageView) view.findViewById(R.id.mr_scrap_icon);
                mr_myhouse = (ImageView) view.findViewById(R.id.mr_myhouse);
            }
        }
    }

    public void recyclerviewinit(ArrayList<MyRoomValueObject> mvo) {

        MyRoomRecyclerViewAdapter adapter = new MyRoomRecyclerViewAdapter(ForRoomApplication.getFRContext());
        adapter.setListItem(mvo);
        rv.setAdapter(adapter);
    }
}
