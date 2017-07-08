package com.forroom.suhyemin.kimbogyun.songmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.fragment.Magazine_info_item1_Fragment;
import com.forroom.suhyemin.kimbogyun.songmin.fragment.Magazine_info_item2_Fragment;
import com.forroom.suhyemin.kimbogyun.songmin.fragment.Magazine_info_item3_Fragment;
import com.forroom.suhyemin.kimbogyun.songmin.fragment.Magazine_info_item4_Fragment;
import com.forroom.suhyemin.kimbogyun.songmin.fragment.Magazine_info_item5_Fragment;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.MagazineInfoValueObject;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Magazine_info_Activity extends AppCompatActivity {
    private ViewPager vp;
    public static MagazineInfoValueObject MZIVO;
    private String id;
    private ImageView vr_icon;
    private AlertDialog.Builder ab;
    int select = 0;
    private LinearLayout replyLinear, scrapLinear;
    String filePath = null;
    TextView reply_count, scrap_count;
    ImageView reply_icon, scrap_icon;
    String[] putComment = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magazine_info_);
        reply_count = (TextView) findViewById(R.id.magazine_info_replycount);
        scrap_count = (TextView) findViewById(R.id.magazine_info_scrapcount);

        vp = (ViewPager) findViewById(R.id.magazine_info_viewpager);
        Intent intent = getIntent();
        id = intent.getStringExtra("toMagazine_info");
//        Log.e("ssong magazine id", id);
        putComment[0] = "mgz";
        putComment[1] = id;

        scrapLinear = (LinearLayout)findViewById(R.id.magazine_info_scrapLinear);
        replyLinear = (LinearLayout)findViewById(R.id.magazine_info_replyLinear);
        reply_icon = (ImageView) findViewById(R.id.magazine_info_replyicon);
        scrap_icon = (ImageView) findViewById(R.id.magazine_info_scrapicon);

        scrapLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncmagazineScrapChangeJSONList().execute(ForRoomApplication.USER_ID);
            }
        });
        replyLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Magazine_info_Activity.this, CommentActivity.class);
                intent.putExtra("toComment", putComment);
                startActivity(intent);
            }
        });
//        reply_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Magazine_info_Activity.this, CommentActivity.class);
//                intent.putExtra("toComment", putComment);
//                startActivity(intent);
//            }
//        });
//
//        scrap_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AsyncmagazineScrapChangeJSONList().execute(ForRoomApplication.USER_ID);
//            }
//        });

        final String items[] = {"Panorama", "VR"};
        ab = new AlertDialog.Builder(Magazine_info_Activity.this);
//        ab.setView(R.id.)

        ab.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) { //리스트

                        select = whichButton;
                    }
                }).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) { //OK
                        if (select == 0) {
                            new PanoramaAsync().execute(ForRoomConstant.TARGET_URL + MZIVO.mgz_vr_pic, MZIVO.mgz_info_id + ".jpg");
                        } else if (select == 1)
                            new VRAsync().execute(ForRoomConstant.TARGET_URL + MZIVO.mgz_vr_pic, MZIVO.mgz_info_id + ".jpg");
                        {
                        }
                    }
                }).setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Cancel 버튼 클릭시
                        dialog.dismiss();
                    }
                });

        vr_icon = (ImageView) findViewById(R.id.magazine_info_vr_icon);
        vr_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select = 0 ;
                ab.show();
            }
        });
        new AsyncTaskMagazineinfoJSONList().execute(ForRoomApplication.USER_ID);

    }

    public class AsyncTaskMagazineinfoJSONList extends AsyncTask<String, Integer, MagazineInfoValueObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected MagazineInfoValueObject doInBackground(String... params) {
            String requestquery = "usrid=" + params[0];
            HttpURLConnection conn = null;
            BufferedReader fromserver = null;
            MagazineInfoValueObject magazineInfo = null;
            try {
                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.MAGAZINE_READ_PATH + id);
                conn = (HttpURLConnection) target.openConnection();
                conn.setConnectTimeout(10000);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                OutputStream toServer = conn.getOutputStream();
                toServer.write(requestquery.getBytes("UTF-8"));
                toServer.close();

                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    fromserver = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = null;
                    StringBuilder jsonBuf = new StringBuilder();
                    while ((line = fromserver.readLine()) != null) {
                        jsonBuf.append(line);
                    }
//                    Log.e("ssong Magazineinfo", jsonBuf + "");

                    magazineInfo = ParseDataParseHandler.getJSONMagazineInfoRequestAllList(jsonBuf);
                }
            } catch (Exception e) {
                Log.e("get JSONmagazineInfo", "서버오류", e);
            } finally {
                if (fromserver != null) {
                    try {
                        fromserver.close();
                    } catch (Exception e) {
                    }
                    conn.disconnect();
                }
            }
            return magazineInfo;
        }

        @Override
        protected void onPostExecute(MagazineInfoValueObject magazineInfoValueObjects) {
            vp.setAdapter(new MagazineInfoViewPagerAdapter(getSupportFragmentManager(), Magazine_info_Activity.this, magazineInfoValueObjects));

            CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);

            indicator.setViewPager(vp);
            indicator.setFillColor(Color.BLACK);
//            indicator.setPageColor(Color.GRAY);
            indicator.setStrokeColor(Color.LTGRAY);
            indicator.setRadius(8f);

            if (magazineInfoValueObjects.mgz_info_scrapcheck) {
                scrap_icon.setImageResource(R.drawable.like_select);
            } else {
                scrap_icon.setImageResource(R.drawable.like);
            }
            reply_count.setText(magazineInfoValueObjects.mgz_info_replycount);
            scrap_count.setText(magazineInfoValueObjects.mgz_info_scrap_count);
            if (!MZIVO.mgz_info_usrvr) {
                vr_icon.setVisibility(View.INVISIBLE);
            }
        }
    }

    static class MagazineInfoViewPagerAdapter extends FragmentPagerAdapter {
        private Context context;

        MagazineInfoViewPagerAdapter(FragmentManager fm, Context context, MagazineInfoValueObject MZIVo) {
            super(fm);
            this.context = context;
            MZIVO = MZIVo;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Magazine_info_item1_Fragment();
                case 1:
                    return new Magazine_info_item2_Fragment();
                case 2:
                    return new Magazine_info_item3_Fragment();
                case 3:
                    return new Magazine_info_item4_Fragment();
                case 4:
                    return new Magazine_info_item5_Fragment();
            }
            return null;
        }

        @Override
        public int getCount() {
//            Log.e("ssong length", MZIVO.mgz_info_pic.length + "");
            return MZIVO.mgz_info_pic.length;
        }
    }

    //VR Image Download & View

    public class VRAsync extends AsyncTask<String, Integer, String> { //VR 실행
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = ProgressDialog.show(Magazine_info_Activity.this, "로딩중", "잠시 기다려주세요", true);
            pd.show();
        }

        @Override
        protected String doInBackground(String... arg) {
            String filePath = null;
            try {
                filePath = exdownloadFile(arg[0], arg[1]);
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            if (s != null) {
                Intent intent = new Intent(Magazine_info_Activity.this, vrActivity.class);
                intent.putExtra("imgpath", filePath);
                startActivity(intent);
            }
        }
    }

    public class PanoramaAsync extends AsyncTask<String, Integer, String> { //파노라마 실행
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = ProgressDialog.show(Magazine_info_Activity.this, "로딩중", "잠시 기다려주세요", true);
            pd.show();
        }

        @Override
        protected String doInBackground(String... arg) {
            try {
                String filePath = exdownloadFile(arg[0], arg[1]);
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return filePath;
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            if (s != null) {
                Intent intent = new Intent(Magazine_info_Activity.this, PhotoActivity.class);
                intent.putExtra("imgpath", s);
                startActivity(intent);
            }
        }
    }

    String exdownloadFile(String strUrl, String fileName) { //외부저장소에 저장.
        int count;
        try {
            URL url = new URL(strUrl);
            URLConnection conection = url.openConnection();
            conection.connect();
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "foroom");
            if (!directory.exists()) {
                directory.mkdir();
            }

            File file = new File(directory + File.separator + fileName);
            if (!file.exists()) {
//                Log.e("다운로드", "^^");
                OutputStream output = new FileOutputStream(directory + File.separator + fileName);

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } else {

//                Log.e("노 다운로드", " ^^");
            }
            filePath = file.toString();
        } catch (Exception e) {
            Log.d("tag", "Image download error.");
            return null;
        }
        return filePath;
    }
    public String downloadFile(String strUrl, String fileName) {
        int count;
        try {
            URL url = new URL(strUrl);
            URLConnection conection = url.openConnection();
            conection.connect();
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            File directory = new File( getFilesDir()  + File.separator + "foroom");
            if (!directory.exists()) {
                directory.mkdir();
            }

            File file = new File(directory + File.separator + fileName);
            if (!file.exists()) {
//                Log.e("다운로드", "^^");
                OutputStream output = new FileOutputStream(directory + File.separator + fileName);

                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;

                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            } else {

//                Log.e("노 다운로드", " ^^");
            }
            filePath = file.toString();
        } catch (Exception e) {
            Log.d("tag", "Image download error.");
            return null;
        }
        return filePath;


    }

//    public String downloadFile(String strUrl, String FN) {
//        Log.e(strUrl , FN);
//        File file = new File(getFilesDir().toString());
////                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath().toString());
//        file.mkdirs();
//        File f = new File(file + File.separator + FN);
//        Log.e(f.toString(), "파일명");
//
//        String fileName =f.toString();
////        String fileName = getFilesDir() + File.separator + FN;
//        //                   URL :  ForRoomConstant.TARGET_URL + goodsItemValueObjects.get(i).goods_image
////                   downloadFile(ForRoomConstant.TARGET_URL + goodsItemValueObj0cts.get(i).goods_image,file2.toString());
//        OutputStream fos;
//        try {
//            URL url = new URL( strUrl);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.connect();
////                    Log.e("파일", f + "");
//            fos = new BufferedOutputStream(new FileOutputStream(f, true));
//            int nSize = conn.getContentLength();
//            BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), nSize);
////                    FileInputStream fis = new FileInputStream(f);  // 2nd line
//            byte[] buf = new byte[1024];
//            int count;
//            while ((count = bis.read(buf)) > 0) {
//                fos.write(buf, 0, count);
//            }
//            conn.disconnect();
//            fos.close();
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return null;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//        return fileName;
//
//    }

    private class AsyncmagazineScrapChangeJSONList extends AsyncTask<String, Integer, String> {
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
                        .append("&docid=" + id);

                URL target = new URL(ForRoomConstant.TARGET_URL + ForRoomConstant.USER_SCRAP_CHANGE + "mgz");
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
                    scrap_icon.setImageResource(R.drawable.like_select);
                    new AsyncTaskMagazineinfoJSONList().execute(ForRoomApplication.USER_ID);
                } else if (s.equalsIgnoreCase("DELETE_SCRAP")) {
                    scrap_icon.setImageResource(R.drawable.like);
                    new AsyncTaskMagazineinfoJSONList().execute(ForRoomApplication.USER_ID);
                }
            } else {
                Toast.makeText(ForRoomApplication.getFRContext(), "서버와 접속이 원활하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}