package com.forroom.suhyemin.kimbogyun.songmin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// 동영상은 res/raw 디렉토리를 생성하여 그 안에다가 넣어놓음..
// manifest 파일에서 시작하는 activity 를 splashactivity 로 바꿔놓음
// activity_splash 는 match_parent 사이즈로 frameLayout을 배치하여 사용

public class SplashActivity extends AppCompatActivity {
    private SplashView mSplashView;
    private MediaPlayer mMediaPlayer;
    private GoogleCloudMessaging gcmMessaging;
    private MediaPlayer.OnPreparedListener videoPreparedListener = new MediaPlayer.OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
            if (mp != null && !mp.isPlaying()) {
                mp.start();
            }
        }
    };
    private MediaPlayer.OnCompletionListener videoCompletionListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
            startMainActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        new getRegistrationID().execute();


//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.forroom.suhyemin.kimbogyun.songmin",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
//        Log.i("mylog", "1");
        //   getSupportActionBar().hide();
//        Log.i("mylog", "2");

//        FrameLayout frame = (FrameLayout) findViewById(R.id.splash_frame);

//
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.forroom.suhyemin.kimbogyun.songmin",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }

//        Log.i("mylog", "3");

    }

    private class getRegistrationID extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mSplashView = new SplashView(SplashActivity.this);

            setContentView(mSplashView);
        }

        @Override
        protected String doInBackground(String... params) {


            //GET GCM RegistrationID

            if (gcmMessaging == null) {
                gcmMessaging = GoogleCloudMessaging.getInstance(getApplicationContext());
            }
            gcmMessaging = GoogleCloudMessaging.getInstance(getApplicationContext());
            String gcmRegistrationID = null;
            try {
                gcmRegistrationID = gcmMessaging.register("373105515136");
                ForRoomApplication.registrationID = gcmRegistrationID;
            } catch (IOException e) {
                Log.e("왜죠", e + "");
                e.printStackTrace();
            }
//        Log.e("발급받은 등록 ID 값 =>", gcmRegistrationID);

            Log.e("레지스트레이션", ForRoomApplication.registrationID + "");
            gcmMessaging.close();

            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void startMainActivity() {
        Intent intent = new Intent(SplashActivity.this, FoRoomLoginActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private class SplashView extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder holder;
        private Context context;

        private void initView() {
            holder = getHolder();
            holder.addCallback(this);
        }

        public SplashView(Context context) {
            super(context);
            this.context = context;
            initView();
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (mMediaPlayer == null) {
                setVideoMediaPlayer(holder);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            releaseMediaPlayer();
        }

        public void setVideoMediaPlayer(SurfaceHolder holder) {
            if (mMediaPlayer == null) mMediaPlayer = new MediaPlayer();
            else mMediaPlayer.reset();

            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.foroom);

            try {
                mMediaPlayer.setDataSource(context, uri);
                mMediaPlayer.setDisplay(holder);
                mMediaPlayer.setOnPreparedListener(videoPreparedListener);
                mMediaPlayer.setOnCompletionListener(videoCompletionListener);
                mMediaPlayer.prepare();
            } catch (Exception e) {
                startMainActivity();
            }
        }
    }
}
