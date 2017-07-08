package com.forroom.suhyemin.kimbogyun.songmin.profilePage;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.forroom.suhyemin.kimbogyun.songmin.ForRoomApplication;
import com.forroom.suhyemin.kimbogyun.songmin.MyRoom_info;
import com.forroom.suhyemin.kimbogyun.songmin.ParseDataParseHandler;
import com.forroom.suhyemin.kimbogyun.songmin.R;
import com.forroom.suhyemin.kimbogyun.songmin.WishRoom_info;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MagazineValueObject;
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
public class ProfileWriteFragment extends Fragment {
    private RecyclerView rv;
    private Spinner spinner;
    private ArrayAdapter<String> arrayAdapter;
    private ProfileScrapRecyclerAdapter adapter =null;
    private List<MyRoomValueObject> writemyroomlist;
    private List<WishRoomValueObject> writewishroomlist;
    static ProfileActivity owner;
    ProfileExpandableAdapter2 expandableAdapter;
    private ExpandableListView expandalbelist;
    String title = "마이룸";
    String one = "마이룸";
    String two = "위시룸";

    public ProfileWriteFragment() {
        // Required empty public constructor
    }

    public static ProfileWriteFragment createInstance() {
        final ProfileWriteFragment pageFragment = new ProfileWriteFragment();
        final Bundle bundle = new Bundle();
        pageFragment.setArguments(bundle);

        return pageFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile_write, container, false);
        rv = (RecyclerView)v.findViewById(R.id.profile_write_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(ForRoomApplication.getFRContext()));

        owner = (ProfileActivity)getActivity();
//        spinner = (Spinner)v.findViewById(R.id.profile_write_spinner);
//        arrayAdapter = new ArrayAdapter<String>(ForRoomApplication.getFRContext(),R.layout.item_scrap_spinner);
//        arrayAdapter.setDropDownViewResource(R.layout.item_spinner_child);
//        arrayAdapter.add("마이룸");
//        arrayAdapter.add("위시룸");
//        spinner.setAdapter(arrayAdapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                Log.e("ssong position", position + "");
//                switch (position) {
//                    case 0:
//                        new AsyncTaskProfileWriteMyRoom().execute();
//                        break;
//                    case 1:
//                        new AsyncTaskProfileWriteWishRoom().execute();
//                        break;
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        expandableAdapter = new ProfileExpandableAdapter2(ForRoomApplication.getFRContext());
        expandalbelist = (ExpandableListView)v.findViewById(R.id.profile_write_expandableListView);
        expandalbelist.setAdapter(expandableAdapter);
        new AsyncTaskProfileWriteMyRoom().execute();

        return v;
    }
    private static class ProfileScrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private Context context;
        private List<MagazineValueObject> magazineItem;
        private List<MyRoomValueObject> myroomItem;
        private List<WishRoomValueObject> wishroomItem;
        int type =0;
        private static final int TYPE_1=0;
        private static final int TYPE_2=1;
        private static final int TYPE_3=2;


        ProfileScrapRecyclerAdapter(Context context,List<MagazineValueObject> list,List<MyRoomValueObject> list2
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
                    holder1.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
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
                    holder2.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ForRoomApplication.getFRContext(), MyRoom_info.class);
                            intent.putExtra("toMyRoomInfo", myroomData.id);
                            intent.putExtra("toMyRoomInfo2", myroomData.mr_scrap_check);
                            owner.startActivity(intent);
                        }
                    });


                    break;
                case TYPE_3:
                    final WishRoomValueObject wishData = wishroomItem.get(position);

                    WishRoomScrapViewHolder holder3 = (WishRoomScrapViewHolder)holder;
                    Glide.with(context).load(ForRoomConstant.TARGET_URL+wishData.wishhouse).into(holder3.scrap_wishroom_image);

                    holder3.mview.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ForRoomApplication.getFRContext(), WishRoom_info.class);
//                            Log.e("ssong", wishData.id);
                            intent.putExtra("toWishRoomInfo", wishData.id);
//                            Log.e("ssong", wishData.wr_scrap_check+"");
                            intent.putExtra("toWishRoomInfo2", wishData.wr_scrap_check);
                            owner.startActivity(intent);
                        }
                    });

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

    private class AsyncTaskProfileWriteMyRoom extends AsyncTask<String, Integer, ArrayList<MyRoomValueObject>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<MyRoomValueObject> doInBackground(String... params) {
            String result = "";
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            StringBuilder queryBuf = new StringBuilder();
            ArrayList<MyRoomValueObject> myroomVO = null;

            try {
                queryBuf.append("usrid=" + ProfileActivity.userid);

                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.USER_WRITE_LIST + "mr");
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
                    myroomVO = ParseDataParseHandler.getWriteMyRoomList(jsonBuf);
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

            return myroomVO;

        }

        @Override
        protected void onPostExecute(ArrayList<MyRoomValueObject> myRoomValueObjects) {
            writemyroomlist = myRoomValueObjects;
            adapter = new ProfileScrapRecyclerAdapter(ForRoomApplication.getFRContext(), null, writemyroomlist, null, 1);
            rv.setAdapter(adapter);
        }
    }

    public class AsyncTaskProfileWriteWishRoom extends AsyncTask<String, Integer, ArrayList<WishRoomValueObject>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<WishRoomValueObject> doInBackground(String... params) {
            String result = "";
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            StringBuilder queryBuf = new StringBuilder();
            ArrayList<WishRoomValueObject> wishroomVO = null;

            try {
                queryBuf.append("usrid=" + ProfileActivity.userid);

                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.USER_WRITE_LIST + "wr");
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
                    wishroomVO =ParseDataParseHandler.getWriteWishRoomList(jsonBuf);
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

            return wishroomVO;
        }

        @Override
        protected void onPostExecute(ArrayList<WishRoomValueObject> wishroomVO) {
            writewishroomlist = wishroomVO;
            adapter = new ProfileScrapRecyclerAdapter(ForRoomApplication.getFRContext(), null, null, writewishroomlist, 2);
            rv.setAdapter(adapter);
        }
    }
    private class ProfileExpandableAdapter2 extends BaseExpandableListAdapter {
        Context context;
        TextView text;


        public ProfileExpandableAdapter2(Context context){
            this.context = context;
        }

        @Override
        public int getGroupCount() {
            return 1;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
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
            View v = convertView;
            v = LayoutInflater.from(context).inflate(R.layout.item_profile_expandable, null);
            text = (TextView)v.findViewById(R.id.profile_expandable_title);
            text.setText(title);
            return v;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View v = convertView;
            v= LayoutInflater.from(context).inflate(R.layout.item_profile_expandable_child, null);
            text = (TextView)v.findViewById(R.id.profile_expandable_child);
            if(childPosition==0){
                text.setText(two);
                text.setTextColor(Color.parseColor("#9b9b9b"));
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String a= title;
                        title=two;
                        two = a;
                        expandableAdapter.notifyDataSetChanged();

                        switch(title){
                            case "위시룸":
                                new AsyncTaskProfileWriteWishRoom().execute();
                                break;
                            case "마이룸":
                                new AsyncTaskProfileWriteMyRoom().execute();
                                break;
                        }
                    }
                });
            }
            return v;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}
