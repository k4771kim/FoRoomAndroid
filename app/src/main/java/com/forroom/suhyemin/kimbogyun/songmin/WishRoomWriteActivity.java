package com.forroom.suhyemin.kimbogyun.songmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.fragment.WishRoomWriteFragment;
import com.forroom.suhyemin.kimbogyun.songmin.fragment.WishRoomWriteJjim;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.WishRoomWriteValueObject;
import com.munon.turboimageview.MultiTouchObject;
import com.munon.turboimageview.TurboImageView;
import com.munon.turboimageview.TurboImageViewListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WishRoomWriteActivity extends AppCompatActivity implements TurboImageViewListener {
    public static  TurboImageView turboImage;
    public static HashMap<String, Integer> goods_id;
    private FrameLayout frameLayout;
    private ImageView delete_all_icon, delete_icon, flip_icon, wr_write_ok_icon;
    WishRoomWriteValueObject WRWVO;
    private File file;
    static Context mContext;
    public static String[] intentarray;
    private ImageView lefticon;
    boolean a = false;
    boolean b = true;
    private TextView categorytext, jjimtext;
    private ImageView categoryicon, jjimicon;


    FrameLayout imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_room_write);

        goods_id = new HashMap<>();



        categoryicon = (ImageView)findViewById(R.id.wr_write_category_icon);
        jjimicon = (ImageView)findViewById(R.id.wr_write_jjim_icon);
        categorytext = (TextView)findViewById(R.id.wr_write_category_text);
        jjimtext =(TextView)findViewById(R.id.wr_write_jjim_text);
        lefticon = (ImageView)findViewById(R.id.wishroom_write_left_icon);
        lefticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        WRWVO = new WishRoomWriteValueObject();
        WRWVO.usrid = ForRoomApplication.USER_ID;
        mContext = getApplicationContext();
        Intent intent = getIntent();
        intentarray = intent.getStringArrayExtra("toWishRoomWrite");
        imageCapture = (FrameLayout) findViewById(R.id.image_capture);
        wr_write_ok_icon =(ImageView)findViewById(R.id.wr_write_ok_icon);
        delete_all_icon = (ImageView) findViewById(R.id.wr_write_delete_all_icon);
        delete_icon = (ImageView)findViewById(R.id.wr_write_delete_icon);
        flip_icon = (ImageView)findViewById(R.id.wr_write_flip_icon);
        turboImage = (TurboImageView) findViewById(R.id.wr_write_turboimageView);
        turboImage.setListener(this);

        wr_write_ok_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turboImage.deselectAll();
                WRWVO.tag = new String[intentarray.length];
                for(int i=0; i<intentarray.length;i++){
                    WRWVO.tag[i] = intentarray[i];
                }
                imageCapture.buildDrawingCache();
                Bitmap tempBitmap = imageCapture.getDrawingCache();
                FileOutputStream fos;

                try{
//                    File toFile =  new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
//                            "/foroom/" );
                    File toFile = new File(getFilesDir()+"/foroom/");
//                    Log.e("ssong", getFilesDir()+"/foroom/");
                    if (!toFile.exists()) {
                        toFile.mkdirs();
                    }
                    file = new File(toFile,System.currentTimeMillis() / 1000 + ".png");
                    fos = new FileOutputStream(file);
                    tempBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
//                    Log.e("저장로직", "캐치전");
                    //imageCapture.setDrawingCacheEnabled(false);
                }catch(IOException ioe) {
                    System.err.println("캡쳐중 에러==>" + ioe);
                }
                WRWVO.wishroomStickerImage = file;
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_title_insert, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(WishRoomWriteActivity.this);
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
                        wr_write_ok_icon.setClickable(false);

                        WRWVO.wishroomTitle = name;

                        new AsyncTaskWishRoomWriteInsert().execute(WRWVO);
                        dialog.dismiss();
                        Intent intent = new Intent();
                        setResult(ForRoomConstant.WISHROOM_WRITE_FILTER_FINISH_RESULT_CODE, intent);

                        finish();
                    }
                });

                iv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Toast.makeText(ForRoomApplication.getFRContext(), "취소눌름", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        delete_all_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turboImage.removeAllObjects();
                goods_id.clear();
            }
        });

        delete_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turboImage.removeSelectedObject();

            }
        });

        flip_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                turboImage.addObject(WishRoomWriteActivity.this,R.drawable.btn_ok );
                turboImage.toggleFlippedHorizontallySelectedObject();
//                Log.e("ssong view", turboImage.getSelectedObjectCount()+"");
            }
        });
        frameLayout = (FrameLayout)findViewById(R.id.wr_write_FrameLayout);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.wr_write_FrameLayout, new WishRoomWriteFragment());
        ft.commit();
        final LinearLayout category = (LinearLayout)findViewById(R.id.wr_write_category_linear);
        LinearLayout jjim = (LinearLayout)findViewById(R.id.wr_write_jjim_linear);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a){
                    categoryicon.setImageResource(R.drawable.icon_non_category);
                    categorytext.setTextColor(Color.parseColor("#010101"));
                    jjimicon.setImageResource(R.drawable.btn_wishbox_off_60);
                    jjimtext.setTextColor(Color.parseColor("#9b9b9b"));
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.wr_write_FrameLayout, new WishRoomWriteFragment());
                    ft.commit();

                    a=false;
                    b=true;

                }
            }
        });

        jjim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b){
                    jjimicon.setImageResource(R.drawable.btn_wishbox_off);
                    jjimtext.setTextColor(Color.parseColor("#010101"));
                    categoryicon.setImageResource(R.drawable.icon_sel_category_60);
                    categorytext.setTextColor(Color.parseColor("#9b9b9b"));
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.wr_write_FrameLayout, new WishRoomWriteJjim());
                    ft.commit();

                    b=false;
                    a=true;
                }
            }
        });
    }
    @Override
    public void onImageObjectSelected(MultiTouchObject object) {
        Log.e("ssong objecty?", object.toString());
    }
    @Override
    public void onImageObjectDropped() {
    }
    @Override
    public void onCanvasTouched() {
        turboImage.deselectAll();
    }

    public class AsyncTaskWishRoomWriteInsert extends AsyncTask<WishRoomWriteValueObject, Integer,String>{
        private final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        OkHttpClient client = new OkHttpClient();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(WishRoomWriteValueObject... params) {
            WishRoomWriteValueObject param = params[0];
            String result = "";
            File file = new File(param.wishroomStickerImage.toString());

           MultipartBody.Builder mb = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addPart(
                            Headers.of("Content-Disposition", "form-data; name=\"usrid\""),
                            RequestBody.create(null, param.usrid))
                    .addPart(
                            Headers.of("Content-Disposition", "form-data; name=\"title\""),
                            RequestBody.create(null, param.wishroomTitle));

            for(int i=0; i<param.tag.length ; i++){
                Log.e("Tag" , param.tag[i]+"");
                if(param.tag[i]=="" || param.tag[i]==null || param.tag[i].length()==0 || param.tag[i].length()==1){

                }else{
//                    Log.e("real Tag", param.tag[i]+"");
                    mb.addPart(Headers.of("Content-Disposition", "form-data; name=\"tag\""),
                            RequestBody.create(null, param.tag[i]));
                }
            }

            Iterator<String> iterator = goods_id.keySet().iterator();
            while(iterator.hasNext()){
                String key = (String)iterator.next();

                mb.addPart(Headers.of("Content-Disposition", "form-data; name=\"goods\""),
                        RequestBody.create(null, key));
            }
//            for(int i=0; i<goods_id.size(); i++){
//                Log.e("goodsid" , goods_id.size()+"");
//
//                mb.addPart(Headers.of("Content-Disposition", "form-data; name=\"goods\""),
//                        RequestBody.create(null, ));
//            }
            mb.addFormDataPart("sticker", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));

            RequestBody body = mb.build();

            Request request = new Request.Builder()
                    .url(ForRoomConstant.TARGET_URL+ForRoomConstant.WISHROOM_WRITE_INSERT_PATH)
                    .post(body)
                    .build();

            Response response = null;
            try{
                response = client.newCall(request).execute();
                return response.body().string();
            }catch(Exception e){
                Log.e("wishRoom Write ", "에러 서버가 잘못함", e);
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Log.e("ssong wishroom",s);
            JSONObject responseData = null;
            String result = null;
            try {
                responseData = new JSONObject(s);
                result = responseData.getString("result");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // OK or FAIL
            if(s!= null){
                if(result !=null)
                if(result.equalsIgnoreCase("SUCCESS")){
//                    Toast.makeText(ForRoomApplication.getFRContext(), "글이 잘 입력 되었다", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public static Context getmContext(){
        return  mContext;
    }
}