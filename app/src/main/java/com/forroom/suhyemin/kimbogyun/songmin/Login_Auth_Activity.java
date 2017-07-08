package com.forroom.suhyemin.kimbogyun.songmin;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.common.PropertyManager;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.GoodsItemValueObject;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.LoginValueObject;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ccei on 2016-02-22.
 */
public class Login_Auth_Activity extends Activity {
    ForoomSQLiteOpenHelper db;
    ProgressDialog PD;
    String Token;
    String name;
    Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        Intent intent = getIntent();



        String way = intent.getStringExtra("login_way");
//        Log.e("웨이" , way+"");
        LoginValueObject loginVO = new LoginValueObject();


        if (way.equalsIgnoreCase("E")) {
//            loginVO.name = name;
//            loginVO.userid = Token;
//            loginVO.way =way;
            new AsyncTaskGoodsItemJSONList().execute();
        } else if (way.equalsIgnoreCase("F")) {
            Token = intent.getStringExtra("login_token");
            name = intent.getStringExtra("user_name");
            loginVO.name = name;
            loginVO.userid = Token;
            loginVO.way = way;
            new AsyncTaskUserAuthLogin().execute(loginVO);
        }
        //TARGET_URL
    }

    public class AsyncTaskUserAuthLogin extends AsyncTask<LoginValueObject, Integer, String> { //페이스북

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(LoginValueObject... params) {
            LoginValueObject param = params[0];
            String result = "";
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            StringBuilder queryBuf = new StringBuilder();
            try {
                queryBuf.append("id=" + param.userid)
                        .append("&name=" + param.name)
                        .append("&way=" + param.way)
                .append("&registration=" + ForRoomApplication.registrationID);

                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.USER_AUTH_LOGIN);
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
                    String line = "";
                    StringBuilder jsonBuf = new StringBuilder();
                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }
                    JSONObject responseData = new JSONObject(jsonBuf.toString());
                    result = responseData.getJSONObject("userinfo").getString("usr_id");
                    ForRoomApplication.USER_ID = result;
                    Log.e("DB 버전", "" + responseData.getInt("goods_version"));
                    ForoomSQLiteOpenHelper.DB_VERSION = responseData.getInt("goods_version");
                    Log.e("디바이스버전", "");

                    double device_version = Double.parseDouble(getPackageManager().getPackageInfo(getPackageName(), 0).versionName); // 앱 버전 체크
                    double appVersion = responseData.getDouble("app_version");

                    if (device_version < appVersion) {
                        result = "UPDATE";
                        ForRoomApplication.enableUpdate = "업데이트가 필요합니다.";
                    }else{
                        ForRoomApplication.enableUpdate = "최신 버전입니다.";
                    }

                    ForRoomApplication.appVersion = device_version +"";

                }
            } catch (Exception e) {
//                Log.e("erroeroerooeoro", "login", e);
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
            if (s == "UPDATE") {
                marketDialog mDialog = new marketDialog(Login_Auth_Activity.this, "F");
                mDialog.show();

            } else {
                PropertyManager.getInstance().setWay("F");
                new AsyncTaskGoodsItemJSONList().execute();
            }

        }
    }


    public class marketDialog extends Dialog {

        Activity mActivity;
        String mWay;

        public marketDialog(Activity activity, String way) {
            super(activity);
            mActivity = activity;
            mWay = way;
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
            lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            lpWindow.dimAmount = 0.8f;
            getWindow().setAttributes(lpWindow);
            setContentView(R.layout.dialog_update_message);

            ImageView iv2 = (ImageView) findViewById(R.id.update_dialog_cancle);
            ImageView iv1 = (ImageView) findViewById(R.id.update_dialog_ok);

            iv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent marketLaunch = new Intent(Intent.ACTION_VIEW);
                    marketLaunch.setData(Uri.parse("market://details?id=com.forroom.suhyemin.kimbogyun.songmin"));
                    mActivity.startActivity(marketLaunch);
                }
            });
            iv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    PropertyManager.getInstance().setWay("F");
                    new AsyncTaskGoodsItemJSONList().execute();
                }
            });
        }

    }

    final int REQUEST_CODE_WRITE = 1;
    public class AsyncTaskGoodsItemJSONList extends AsyncTask<String, Integer, ArrayList<GoodsItemValueObject>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<GoodsItemValueObject> doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader fromServer = null;
            ArrayList<GoodsItemValueObject> goodVOList = null;

            try {
                goodVOList = new ArrayList<>();
                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.GOODS_LIST_UPDATE);
                conn = (HttpURLConnection) target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setRequestMethod("GET");

//                Log.e("ssong 1", "ㅇ");
                int resCode = conn.getResponseCode();
//                Log.e("ssong 12", "ㅇ");
                if (resCode == HttpURLConnection.HTTP_OK) {
//                    Log.e("sss", "ss");
                    fromServer = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = null;
                    StringBuilder jsonBuf = new StringBuilder();
                    while ((line = fromServer.readLine()) != null) {
                        jsonBuf.append(line);
                    }
//                    Log.e("ssong goods", jsonBuf + "");
                    goodVOList = ParseDataParseHandler.getHSONGoodsRequestAllList(jsonBuf);
                }
            } catch (Exception e) {
                Log.i("AsynctaskGoodItem", "dd", e);
            } finally {
                if (fromServer != null) {
                    try {
                        fromServer.close();
                    } catch (Exception e) {
                    }
                    conn.disconnect();
                }
            }
            return goodVOList;
        }

        @Override
        protected void onPostExecute(ArrayList<GoodsItemValueObject> goodsItemValueObjects) {

    db = new ForoomSQLiteOpenHelper();
//            Log.e("ssong", "dddd" + ForoomSQLiteOpenHelper.DB_VERSION);
//            Log.e("ssong", "sdfdfsf" + PropertyManager.getInstance().getGoodVersion());

            if (ForoomSQLiteOpenHelper.DB_VERSION != PropertyManager.getInstance().getGoodVersion()) {
//                Log.e("ssong db change", "ddd");

                //퍼미션 권한 요청


                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    mgoodsItemValueObjects = goodsItemValueObjects;
                    // Request missing location permission.
                    ActivityCompat.requestPermissions(Login_Auth_Activity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_CODE_WRITE);

                } else {
                    // Location permission has been granted, continue as usual.
//                    db.onUpgrade(db.getWritableDatabase(),PropertyManager.getInstance().getGoodVersion(),ForoomSQLiteOpenHelper.DB_VERSION);
                    new ImageDownloadAsync().execute(goodsItemValueObjects); //ImageDownload
//                String path = "/data/data/com.forroom.suhyemin.kimbogyun.songmin/files/foroom/";

                    PropertyManager.getInstance().setGoodVersion(ForoomSQLiteOpenHelper.DB_VERSION);
                }
                //여기까지

//                Log.e("ssong", "sdfdfsf2" + PropertyManager.getInstance().getGoodVersion());
            } else {
                Intent MainIntent = new Intent(Login_Auth_Activity.this, MainActivity.class);
                startActivity(MainIntent);
                finish();
            }
        }

    }
    ArrayList<GoodsItemValueObject> mgoodsItemValueObjects;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_WRITE) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // success!
            //    db.onUpgrade(db.getWritableDatabase(),PropertyManager.getInstance().getGoodVersion(),ForoomSQLiteOpenHelper.DB_VERSION);
                new ImageDownloadAsync().execute(mgoodsItemValueObjects); //ImageDownload
//                String path = "/data/data/com.forroom.suhyemin.kimbogyun.songmin/files/foroom/";

                PropertyManager.getInstance().setGoodVersion(ForoomSQLiteOpenHelper.DB_VERSION);

            } else {
                // Permission was denied or request was cancelled
                Toast.makeText(getApplicationContext(), "퍼미션 권한요청을 취소하셨습니다. ", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public class ImageDownloadAsync extends AsyncTask<ArrayList<GoodsItemValueObject>, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            PD = new ProgressDialog(Login_Auth_Activity.this);
            PD.setTitle("초기 데이터를 구축중입니다. 잠시만 기다려 주세요.");
            PD.setCancelable(false);
            PD.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            PD.dismiss();
            Intent MainIntent = new Intent(Login_Auth_Activity.this, MainActivity.class);
            startActivity(MainIntent);
            finish();
            PropertyManager.getInstance().setGoodVersion(ForoomSQLiteOpenHelper.DB_VERSION);
        }

        @Override
        protected String doInBackground(ArrayList<GoodsItemValueObject>... params) {
            for (int i = 0; i < params[0].size(); i++) {
                if(ForoomSQLiteOpenHelper.getInstace().insertGoodsItem(params[0].get(i))) {
                    File file = new File(getFilesDir().toString());
//                File resources.file = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString());
                    file.mkdirs();
                    File f = new File(file + File.separator + params[0].get(i).goods_id + ".png");
//                Log.e(f.toString(), "파일명");
                    String fileName = getFilesDir() + File.separator + params[0].get(i).goods_id + ".png";
//                   URL :  ForRoomConstant.TARGET_URL + goodsItemValueObjects.get(i).goods_image
//                    downloadFile(ForRoomConstant.TARGET_URL + goodsItemValueObjects.get(i).goods_image,file2.toString());
                    OutputStream fos;
                    try {
                        URL url = new URL(ForRoomConstant.TARGET_URL + params[0].get(i).goods_image);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.connect();
//                    Log.e("파일", f + "");
                        fos = new BufferedOutputStream(new FileOutputStream(f, true));
                        int nSize = conn.getContentLength();
                        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);
//                    FileInputStream fis = new FileInputStream(f);  // 2nd line
                        byte[] buf = new byte[1024];
                        int count;
                        while ((count = bis.read(buf)) > 0) {
                            fos.write(buf, 0, count);
                        }
                        conn.disconnect();
                        fos.close();
                    } catch (Exception e) {
                        Log.d("tag", "Image download error.", e);
                    }
                }
            }
            return null;
        }
    }


}