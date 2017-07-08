package com.forroom.suhyemin.kimbogyun.songmin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.common.LabelImageView;
import com.forroom.suhyemin.kimbogyun.songmin.common.LabelTag;
import com.forroom.suhyemin.kimbogyun.songmin.fragment.MyRoomWriteBottomFragment;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MyRoomWriteValueObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyRoomWriteActivity extends AppCompatActivity {
    private String[] galleryimage;
    private String[] image_text;
    private RecyclerView rv;
    private TextView mr_write_text_bottom1, mr_write_text_bottom2, mr_write_text_bottom3;
    public static ArrayList<String> myroomwritetag;
    public static  TextView mr_write_tag1, mr_write_tag2, mr_write_tag3, mr_write_tag4, mr_write_tag5;
//    public static String tag1, tag2, tag3, tag4, tag5;
//    public static ArrayList<LabelTag>[] LabelTagArrayListArray;
//    public ArrayList<LabelTag>[] OLD_LabelTagArrayListArray;
    private ImageView leftIconBack;
    MyRoomWriteValueObject MRWVO;
    MyRoomWriteRecyclerViewAdapter MyRoomWriteRecyclerViewAdapter;
    ImageButton okBtn;
    ArrayList<MyRoomsmallrecyclerVO> myroomrecyclerdata;
    public final int[] tagresource = {R.drawable.btn_t_wood_01_2, R.drawable.btn_t_white_02_2, R.drawable.btn_t_grey_03_2, R.drawable.btn_t_beige_04_2, R.drawable.btn_t_green_05_2,
            R.drawable.btn_t_violet_06_2, R.drawable.btn_t_red_07_2, R.drawable.btn_t_black_08_2, R.drawable.btn_t_blue_09_2, R.drawable.btn_t_gold_10_2,
            R.drawable.btn_t_brown_11_2, R.drawable.btn_t_pink_12_2};
    //
    public static Boolean i1_1=true, i1_2=true, i1_3=true, i1_4=true, i1_5=true, i1_6=true;
    public static Boolean i2_1=true, i2_2=true, i2_3=true, i2_4=true;
    public static Boolean i3_1=true, i3_2=true, i3_3=true, i3_4=true,i3_5=true ,i3_6=true, i3_7=true, i3_8=true, i3_9=true;
    //

    private ArrayList<MyRoomsmallrecyclerVO> myroomData;
    public static int height;
    public static int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ForRoomApplication.setTagContext(MyRoomWriteActivity.this);
        setContentView(R.layout.activity_my_room_write);

        MRWVO = new MyRoomWriteValueObject();
        leftIconBack = (ImageView)findViewById(R.id.myroom_write_backicon);
        leftIconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        myroomwritetag = new ArrayList<>();
        mr_write_tag1 = (TextView)findViewById(R.id.mr_write_tag1);
        mr_write_tag2 = (TextView)findViewById(R.id.mr_write_tag2);
        mr_write_tag3 = (TextView)findViewById(R.id.mr_write_tag3);
        mr_write_tag4 = (TextView)findViewById(R.id.mr_write_tag4);
        mr_write_tag5 = (TextView)findViewById(R.id.mr_write_tag5);

        Display display = getWindowManager().getDefaultDisplay();
        height = display.getHeight();
        width = display.getWidth();

        okBtn = (ImageButton) findViewById(R.id.write_ok_icon);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_title_insert, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MyRoomWriteActivity.this);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();

                dialog.setCanceledOnTouchOutside(false);
                dialog.show();

                ImageView iv2 = (ImageView)dialogView.findViewById(R.id.dialog_cancle);
                ImageView iv1 = (ImageView)dialogView.findViewById(R.id.dialog_ok);

                iv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText edit_name = (EditText) dialogView.findViewById(R.id.dialog_edit);
                        String name = edit_name.getText().toString();
                        okBtn.setClickable(false);

                        MRWVO.myroomtitle = name;
                        new myRoomUploadAsync().execute(MRWVO);
                        dialog.dismiss();
//                        Intent intent = new Intent();
//                        setResult(, intent);

                        Intent intent = new Intent();
                        setResult(ForRoomConstant.MYROOM_WRITE_BACK, intent);
                        finish();
                    }
                });

                iv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ForRoomApplication.getFRContext(), "취소눌름", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }
        });


        Intent intent = getIntent();

        myroomrecyclerdata = new ArrayList<>();
        int returedSize = intent.getStringArrayExtra("tomyroomwrite").length;
        galleryimage = new String[returedSize];
        for(int i=0; i< returedSize; i++){
            MyRoomsmallrecyclerVO sd = new MyRoomsmallrecyclerVO();
            galleryimage[i] = intent.getStringArrayExtra("tomyroomwrite")[i];
            Log.e("갤러리 이미지 ",   galleryimage[i]+"" );
            sd.image =intent.getStringArrayExtra("tomyroomwrite")[i];
            sd.text = null;
            myroomrecyclerdata.add(sd);

        }

        rv = (RecyclerView)findViewById(R.id.mr_write_recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(MyRoomWriteActivity.this));

        MyRoomWriteRecyclerViewAdapter = new MyRoomWriteRecyclerViewAdapter(MyRoomWriteActivity.this, myroomrecyclerdata);
        rv.setAdapter(MyRoomWriteRecyclerViewAdapter);

        mr_write_text_bottom1 = (TextView)findViewById(R.id.mr_write_bottom1);
        mr_write_text_bottom2 = (TextView)findViewById(R.id.mr_write_bottom2);
        mr_write_text_bottom3 = (TextView)findViewById(R.id.mr_write_bottom3);

        mr_write_text_bottom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyRoomWriteBottomFragment(0).show(getSupportFragmentManager(), R.id.mr_write_bottomsheet);
            }
        });

        mr_write_text_bottom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyRoomWriteBottomFragment(1).show(getSupportFragmentManager(), R.id.mr_write_bottomsheet);
            }
        });

        mr_write_text_bottom3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyRoomWriteBottomFragment(2).show(getSupportFragmentManager(), R.id.mr_write_bottomsheet);
            }
        });
    }
/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == ForRoomConstant.MYROOM_WRITE_TAG_FINISH){
            int position = data.getIntExtra("position", 0);



*//*
 RelativeLayout RL = MyRoomWriteRecyclerViewAdapter.RelArray[position];
            for (int i = 0; i < OLD_LabelTagArrayListArray[position].size(); i++) {
                RL.removeView(OLD_LabelTagArrayListArray[position].get(i));
            }
            OLD_LabelTagArrayListArray[position].clear();*//*

*//*            for (int i = 0; i < LabelImageView.tagArr.size(); i++) {
                RL.removeView(LabelImageView.tagArr.get(i));
                LabelTag LT = new LabelTag(getApplicationContext());

                LT.MoveXY((LabelTagArrayListArray[position].get(i).getDataX() * MyRoomWriteActivity.getWidth()), (LabelTagArrayListArray[position].get(i).getDataY() * (RL.getBottom() - RL.getTop())), 1, 2);
                LT.setBackgroundResource(tagresource[MyRoomWriteActivity.LabelTagArrayListArray[position].get(i).getColor() - 1]);
                LT.setText(MyRoomWriteActivity.LabelTagArrayListArray[position].get(i).getText());

                LT.movable = false;
                RL.addView(LT);
                OLD_LabelTagArrayListArray[position].add(LT);
            }*//*
        }

    }


    */
    private class myRoomUploadAsync extends AsyncTask<MyRoomWriteValueObject, Void, String> {
        MultipartBody.Builder MB;
        final MediaType pngType = MediaType.parse("image/png");
//        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd = new ProgressDialog(MyRoomWriteActivity.this);
//            pd.setTitle("업로드하는 중입니다. 잠시만 기다려 주세요.");
//
//            pd.show();
            MB = new MultipartBody.Builder();
            MB.setType(MultipartBody.FORM);
            MB.addFormDataPart("usrid" , ForRoomApplication.USER_ID+"");
            for (int i = 1; i <= galleryimage.length; i++) {
                File f = new File(galleryimage[i - 1]);
                MB.addFormDataPart("pic" + i, f.getName(), RequestBody.create(pngType, f));
                if(myroomData.get(i-1).text==null || myroomData.get(i-1).text.length()==0){
                    MB.addFormDataPart("text" + i, "");
                }else {
                    MB.addFormDataPart("text" + i, myroomData.get(i-1).text);
                }

/*                for (int j = 0; j < LabelTagArrayListArray[i - 1].size(); j++) {
                    MB.addFormDataPart("pic" + i + "x", String.valueOf(LabelTagArrayListArray[i - 1].get(j).getDataX()));
                    MB.addFormDataPart("pic" + i + "y", String.valueOf(LabelTagArrayListArray[i - 1].get(j).getDataY()));
                    MB.addFormDataPart("pic" + i + "txt", String.valueOf(LabelTagArrayListArray[i - 1].get(j).getText()));
                    MB.addFormDataPart("pic" + i + "color", String.valueOf(LabelTagArrayListArray[i - 1].get(j).getColor()));
                }*/
            }

        }

        @Override
        protected String doInBackground(MyRoomWriteValueObject... params) {
            boolean flag = false;
            MyRoomWriteValueObject myroomwriteVO = params[0];

//            MB.addFormDataPart("usrid", ForRoomApplication.USER_ID+"");
            MB.addFormDataPart("title", myroomwriteVO.myroomtitle);
            for(int i=0 ; i<myroomwritetag.size(); i++){
                if(i ==0){
                    MB.addFormDataPart("tag", myroomwritetag.get(i));
                }else if(i==1){
                    MB.addFormDataPart("tag", myroomwritetag.get(i));
                }else if(i==2){
                    MB.addFormDataPart("tag", myroomwritetag.get(i));
                }else if (i == 3) {
                    MB.addFormDataPart("tag", myroomwritetag.get(i));
                }else if(i==4){
                    MB.addFormDataPart("tag", myroomwritetag.get(i));
                }
            }

            try {
                //업로드는 타임 및 리드타임을 넉넉히 준다.
                OkHttpClient toServer = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();

                RequestBody fileUploadBody = MB.build();
                //요청 세팅
                Request request = new Request.Builder()
                        .url(ForRoomConstant.TARGET_URL + ForRoomConstant.MYROOM_INSERT)
                        .post(fileUploadBody) //반드시 post로
                        .build();
                //동기 방식
                Response response = toServer.newCall(request).execute();

                flag = response.isSuccessful();
                //응답 코드 200등등
                int responseCode = response.code();
                if (flag) {
                    Log.e("response결과", response.message()); //읃답에 대한 메세지(OK)
                    Log.e("response응답바디", response.body().string()); //json으로 변신
                }
            } catch (UnknownHostException une) {
                Log.e("fileUpLoad", une.toString());
            } catch (UnsupportedEncodingException uee) {
                Log.e("fileUpLoad", uee.toString());
            } catch (Exception e) {
                Log.e("fileUpLoad", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            pd.dismiss();
        }
    }

    private class MyRoomWriteRecyclerViewAdapter extends RecyclerView.Adapter<MyRoomWriteRecyclerViewAdapter.ViewHolder>{
        private Context context;
//        private RelativeLayout[] RelArray;
        private MyRoomWriteValueObject mrwVO;

        MyRoomWriteRecyclerViewAdapter(Context context, ArrayList<MyRoomsmallrecyclerVO> image){
            this.context = context;
            myroomData = image;

            mrwVO = new MyRoomWriteValueObject();
/*            LabelTagArrayListArray = new ArrayList[myroomData.size()];
            OLD_LabelTagArrayListArray = new ArrayList[myroomData.size()];
            RelArray = new RelativeLayout[myroomData.size()];

            for (int i = 0; i < myroomData.size(); i++) {
                LabelTagArrayListArray[i] = new ArrayList<>();
                OLD_LabelTagArrayListArray[i] = new ArrayList<>();
            }*/
        }
        @Override
        public MyRoomWriteRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_myroom_write, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyRoomWriteRecyclerViewAdapter.ViewHolder holder,final int position) {




            Glide.with(ForRoomApplication.getFRContext()).load(myroomData.get(position).image).signature(new StringSignature(System.currentTimeMillis()+"")).skipMemoryCache(true).into(holder.mr_write_Image);
//            mrwVO.myroomtag = new String[imagearray.length];
//            for(int i=0; i<mrwVO.myroomtag.length;i++){
//                mrwVO.myroomtag[i] = null;
//            }
            holder.mr_write_editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    Log.e("ssong test  :"+position, s.toString());

                    myroomData.get(position).text = s.toString();
                }
                @Override
                public void afterTextChanged(Editable s) {
                }
            });
            if(position==0){
                if(myroomData.get(0).text !=null){
                    holder.mr_write_editText.setText(myroomData.get(position).text);
                }
            }else if(position ==1){
                if(myroomData.get(1).text !=null){
                    holder.mr_write_editText.setText(myroomData.get(position).text);
                }
            }else if(position ==2){
                if(myroomData.get(position).text !=null){
                    holder.mr_write_editText.setText(myroomData.get(position).text);
                }
            }else if(position ==3){
                if(myroomData.get(position).text !=null){
                    holder.mr_write_editText.setText(myroomData.get(position).text);
                }
            }else if(position ==4){
                if(myroomData.get(position).text !=null){
                    holder.mr_write_editText.setText(myroomData.get(position).text);
                }
            }

//            RelArray[position] = holder.mr_write_Relative;
/*            holder.mr_write_Image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyRoomWriteActivity.this, MyRoomImageTagActivity.class);
                    intent.putExtra("toTagActivity", galleryimage[position]);
                    intent.putExtra("kbgposition", position);
//                    startActivity(intent);
                    startActivityForResult(intent, 1);
                }
            });*/
        }

        @Override
        public int getItemCount() {
            return myroomData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public EditText mr_write_editText;
            public ImageView mr_write_Image;
            public View mview;
//            public RelativeLayout mr_write_Relative;
            public ViewHolder(View view){
                super(view);
                mview = view;
                mr_write_editText = (EditText)view.findViewById(R.id.mr_write_text);
                mr_write_Image = (ImageView)view.findViewById(R.id.mr_write_image);
//                mr_write_Relative = (RelativeLayout) view.findViewById(R.id.mr_write_relative);

            }
        }
    }
    public static int getHeight() {
        return height;
    }
    public static int getWidth() {
        return width;
    }

    public class MyRoomsmallrecyclerVO{
        private String image;
        private String text;
        public MyRoomsmallrecyclerVO(){}
    }
}
