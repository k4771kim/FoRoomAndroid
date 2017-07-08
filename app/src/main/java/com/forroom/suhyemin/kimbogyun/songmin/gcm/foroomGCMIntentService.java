/*
 *  BR���� �Ѱܹ��� GCM���� ����Ʈ�� Queue�� �־�
 *  �ϳ��� ó���ϴ� Service Component
 *  author  PYO IN SOO
 */
package com.forroom.suhyemin.kimbogyun.songmin.gcm;

import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Set;

import com.forroom.suhyemin.kimbogyun.songmin.Login_Auth_Activity;
import com.forroom.suhyemin.kimbogyun.songmin.R;
import com.forroom.suhyemin.kimbogyun.songmin.SplashActivity;
import com.forroom.suhyemin.kimbogyun.songmin.common.PropertyManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class foroomGCMIntentService extends IntentService {

    private static final String DEBUG_TAG = "foroomGCMIntentService";
    private NotificationManager notiManager;
    private Notification notification;
    private static int NOTI_NUMBER;

    public foroomGCMIntentService() {
        super("foroomIntentService()");
    }

    @Override
    public void onHandleIntent(Intent intentFromGCMServer) {

        StringBuilder buf = null;
        GoogleCloudMessaging gcmController = GoogleCloudMessaging.getInstance(this);
        String gcmMessageType = gcmController.getMessageType(intentFromGCMServer);

        Log.e("INTENTSERVICE=>", String.valueOf(gcmMessageType));

        if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(gcmMessageType)) {
            buf = new StringBuilder();
            Bundle bundle = intentFromGCMServer.getExtras();
            Set<String> bundleKeys = bundle.keySet();
            Iterator<String> keyValues = bundleKeys.iterator();
            while (keyValues.hasNext()) {
                String key = keyValues.next();
                buf.append(key + ":" + bundle.getString(key) + "\n");
            }
            Log.e(DEBUG_TAG, " MESSAGE_TYPE_SEND_ERROR!" + buf.toString());


        } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(gcmMessageType)) {
            buf = new StringBuilder();
            Bundle bundle = intentFromGCMServer.getExtras();
            Set<String> bundleKeys = bundle.keySet();
            Iterator<String> keyValues = bundleKeys.iterator();
            while (keyValues.hasNext()) {
                String key = keyValues.next();
                buf.append(key + ":" + bundle.getString(key) + "\n");
            }
            Log.e(DEBUG_TAG, " MESSAGE_TYPE_DELETED!" + buf.toString());


        } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(gcmMessageType)) {


            notiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notification = new Notification();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            notification.defaults = Notification.DEFAULT_VIBRATE;

            String messageType = intentFromGCMServer.getStringExtra("type");
            String messageFromGCM = null;
            String titleFromGCM = null;

            try {
                messageFromGCM = URLDecoder.decode(intentFromGCMServer.getStringExtra("message"), "UTF-8");
                titleFromGCM = URLDecoder.decode(intentFromGCMServer.getStringExtra("title"), "UTF-8");
            } catch (Exception e) {
                Log.e(DEBUG_TAG, "DECODE_ERROR = " + e);
            }

            if (messageType != null) {
//				notiManager.notify(NOTI_NUMBER, notification);
                if(PropertyManager.getInstance().getPushSwitch().equalsIgnoreCase("true"))
                sendNotification(titleFromGCM, messageFromGCM);

            }

            try {
                Thread.sleep(5000);
                //	Log.e("SLEEP", "close=" + SystemClock.elapsedRealtime());
            } catch (InterruptedException e) {
            }
            foroomGCMBroadcastReceiver.completeWakefulIntent(intentFromGCMServer);
        }
    }


    private void sendNotification(String title, String msg) {
        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, SplashActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.app_icon)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(123123, mBuilder.build());
    }

}