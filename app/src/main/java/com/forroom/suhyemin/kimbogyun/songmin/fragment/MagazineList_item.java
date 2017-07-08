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
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.flipboard.bottomsheet.commons.BottomSheetFragment;
import com.forroom.suhyemin.kimbogyun.songmin.ForRoomApplication;
import com.forroom.suhyemin.kimbogyun.songmin.Magazine_info_Activity;
import com.forroom.suhyemin.kimbogyun.songmin.MainActivity;
import com.forroom.suhyemin.kimbogyun.songmin.ParseDataParseHandler;
import com.forroom.suhyemin.kimbogyun.songmin.R;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MagazineListInfoValueObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MagazineList_item extends BottomSheetFragment {
    RecyclerView rv;
    static MainActivity owner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_magazine_list_item, container, false);
        owner = (MainActivity)getActivity();

        LinearLayout ll = (LinearLayout)v.findViewById(R.id.magazine_list_linear);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        rv = (RecyclerView)v.findViewById(R.id.magazine_list_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(ForRoomApplication.getFRContext()));

        new AsyncTaskMagazineListJSONList().execute(MagazineFragment.viewpagerposition);

        return v;
    }

    public class AsyncTaskMagazineListJSONList extends AsyncTask<Integer, Integer, ArrayList<MagazineListInfoValueObject>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<MagazineListInfoValueObject> doInBackground(Integer... params) {
            int toserver = params[0];
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            ArrayList<MagazineListInfoValueObject> magazinelist = null;
            try{
//                Log.e("dd", ForRoomConstant.TARGET_URL+ForRoomConstant.MAGAZINE_LIST_PATH+(toserver+1));
                URL target = new URL(ForRoomConstant.TARGET_URL+ForRoomConstant.MAGAZINE_LIST_PATH+(toserver+1));
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
//                        Log.e("while", line);
                    }
//                    Log.e("데이터",jsonBuf.toString());
                    magazinelist = ParseDataParseHandler.getMagazineList(jsonBuf);
                }

            }catch(Exception e){
                Log.i("Asynctaskmagazine", "dd", e);

            }finally {
                if(fromServer != null){
                    try {
                        fromServer.close();
                    }catch(Exception e){
                    }
                    conn.disconnect();
                }
            }
//            Log.e("server", "return");
            return magazinelist;

        }

        @Override
        protected void onPostExecute(ArrayList<MagazineListInfoValueObject> magazineListInfoValueObjects) {
//            Log.e("magazine listsize",magazineListInfoValueObjects.size() +"");
            rv.setAdapter(new MagazineRecyclerViewAdapter(ForRoomApplication.getFRContext(), magazineListInfoValueObjects));
        }
    }
    public static class MagazineRecyclerViewAdapter extends RecyclerView.Adapter<MagazineRecyclerViewAdapter.ViewHolder>{
        private Context context;
        private ArrayList<MagazineListInfoValueObject> magazineListItem;

        public MagazineRecyclerViewAdapter(Context context, ArrayList<MagazineListInfoValueObject> magazine){
            this.context = context;
            this.magazineListItem = magazine;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_magazine_list, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final MagazineListInfoValueObject magazineData = magazineListItem.get(position);

            Glide.with(context).load(ForRoomConstant.TARGET_URL+magazineData.mgz_image).into(holder.magazine_image);
            holder.mview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ForRoomApplication.getFRContext(), Magazine_info_Activity.class);
                    intent.putExtra("toMagazine_info", magazineData.mgz_id);

                    owner.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return magazineListItem.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public ImageView magazine_image;
            public View mview;
            public ViewHolder(View v){
                super(v);
                mview = v;
                magazine_image = (ImageView)v.findViewById(R.id.magazine_list_item_image);
            }
        }
    }


}
