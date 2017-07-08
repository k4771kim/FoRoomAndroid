package com.forroom.suhyemin.kimbogyun.songmin;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.CommentValueObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentActivity extends AppCompatActivity {
    private RecyclerView rv;
    private String type;
    private String id;
    private EditText edittext;
    private ImageView rightIcon;
    private ImageView okbtn;
    private CommentRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        okbtn = (ImageView)findViewById(R.id.comment_ok_btn);
        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edittext.getText().toString()=="" ||edittext.getText().toString()==null || edittext.length()==0){
                    Toast.makeText(ForRoomApplication.getFRContext(), "댓글을 입력해 주세요", Toast.LENGTH_SHORT).show();
                }else{
                    String s =edittext.getText().toString();
//                    Log.e("ssong", edittext.length()+"");
                    new AsyncTaskCommentInsertJSONList().execute(s);
                    edittext.setText("");
                }
            }
        });

        rightIcon = (ImageView)findViewById(R.id.comment_right_icon);
        edittext = (EditText)findViewById(R.id.comment_edittext);
        rv = (RecyclerView)findViewById(R.id.comment_recyclerview);
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rv.setLayoutManager(new LinearLayoutManager(ForRoomApplication.getFRContext()));

        Intent intent = getIntent();
        type = intent.getStringArrayExtra("toComment")[0];
        id = intent.getStringArrayExtra("toComment")[1];

        new AsyncTaskCommentJSONList().execute();
//        Log.e("ssong", type);
//        Log.e("ssong", id);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        if(type.equalsIgnoreCase("wr")){
            setResult(ForRoomConstant.WISHROOM_COMMENT_BACK_RFESULT_CODe, intent);
        }else if(type.equalsIgnoreCase("mr")){
            setResult(ForRoomConstant.MYROOM_COMMENT_BACK_RFESULT_CODE, intent);
        }
        finish();
    }

    public class AsyncTaskCommentJSONList extends AsyncTask<String, Integer, ArrayList<CommentValueObject>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<CommentValueObject> doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            ArrayList<CommentValueObject> commentList = null;
            try{
                URL target=new URL(ForRoomConstant.TARGET_URL+ForRoomConstant.COMMENT_LIST_PATH+type+"/"+id);
//                Log.e("ssong", target+"");
                conn = (HttpURLConnection)target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setRequestMethod("GET");

                int resCode = conn.getResponseCode();
                if(resCode == HttpURLConnection.HTTP_OK){
                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = null;
                    StringBuilder jsonBuf = new StringBuilder();
//                    Log.e("ssong", jsonBuf+"");
                    while((line=fromServer.readLine())!=null){
                        jsonBuf.append(line);
                    }
                    commentList = ParseDataParseHandler.getJSONCommentRequestAllList(jsonBuf);
                }
            }catch(Exception e){

            }finally {
                if(fromServer !=null){
                    try{
                        fromServer.close();
                    }catch(Exception e){

                    }
                    conn.disconnect();
                }
            }
            return commentList;
        }

        @Override
        protected void onPostExecute(ArrayList<CommentValueObject> commentValueObjects) {
            if(commentValueObjects != null && commentValueObjects.size()>0){
                adapter = new CommentRecyclerViewAdapter(ForRoomApplication.getFRContext(), commentValueObjects);
                rv.setAdapter(adapter);
            }
        }
    }

    private static class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder>{
        private Context context;
        private List<CommentValueObject> commentItem;

        CommentRecyclerViewAdapter(Context context, List<CommentValueObject> CVO) {
            this.context = context;
            this.commentItem = CVO;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final CommentValueObject commentData = commentItem.get(position);

            holder.cm_text.setText(commentData.rp_text);
            holder.cm_time.setText(commentData.rp_time);
            holder.cm_usrNick.setText(commentData.rp_nickname);
            Glide.with(context).load(ForRoomConstant.TARGET_URL+commentData.rp_profile).error(R.drawable.profile).skipMemoryCache(true).into(holder.cm_usrPic);
        }

        @Override
        public int getItemCount() {
            return commentItem.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public TextView cm_text, cm_usrNick, cm_time;
            public CircleImageView cm_usrPic;
            public View mview;

            ViewHolder(View view){
                super(view);
                mview = view;
                cm_text =(TextView)view.findViewById(R.id.Comment_text);
                cm_usrNick = (TextView)view.findViewById(R.id.Comment_nickname);
                cm_time = (TextView)view.findViewById(R.id.Comment_time);
                cm_usrPic = (CircleImageView)view.findViewById(R.id.Comment_profile);
            }
        }
    }
    public class AsyncTaskCommentInsertJSONList extends AsyncTask<String, Integer, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            StringBuilder queryBuf = new StringBuilder();
            String result="";

            try{
                queryBuf.append("usrid="+ForRoomApplication.USER_ID)
                        .append("&docid="+id)
                        .append("&text=" + params[0]);
//                Log.e("ssong id", ForRoomApplication.USER_ID);
//                Log.e("ssong docid", id);
//                Log.e("ssong text", params[0]);
                URL target=new URL(ForRoomConstant.TARGET_URL+ForRoomConstant.COMMENT_INSERT_PATH+type);
//                Log.e("ssong", target+"");
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

                    String line = null;
                    StringBuilder jsonBuf = new StringBuilder();
//                    Log.e("ssong", jsonBuf+"");
                    while((line=fromServer.readLine())!=null){
                        jsonBuf.append(line);
                    }
                    JSONObject respinseData = new JSONObject(jsonBuf.toString());
                    result = respinseData.getString("result");

                }
            }catch(Exception e){

            }finally {
                if(fromServer !=null){
                    try{
                        fromServer.close();
                    }catch(Exception e){

                    }
                    conn.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Log.e("ssong test",s);
            new AsyncTaskCommentJSONList().execute();
            if(s!=null){
                if(s.equalsIgnoreCase("SUCCESS")){
//                    Toast.makeText(ForRoomApplication.getFRContext(), "글이 입력이 성공하였습니다.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForRoomApplication.getFRContext(),"서버와 접속이 원활하지 않습니다. 다시 시도해 주세요",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
