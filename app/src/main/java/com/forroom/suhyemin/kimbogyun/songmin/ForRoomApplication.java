package com.forroom.suhyemin.kimbogyun.songmin;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

public class ForRoomApplication extends Application {
    private static Context context;
    private static Typeface mTypeface = null;
    private static Context tagContext;
    public static  String USER_ID;   //4~14 : 테스트아이디  // 8 : 혜민님 아이디  // 10번이 나 // 9번이 보균이형
    public static String registrationID ;
    public static String appVersion, enableUpdate;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

    }

    public static Context getFRContext() {
        return context;
    }

    public static void setTagContext(Context context) {
        tagContext = context;

    }

    public static Context getTagContext() {
        return tagContext;
    }
}
