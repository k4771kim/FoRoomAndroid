/*
 * ��� ���� �ܸ����� CPU�� ����� ����Ʈ�� IntentService�� ������
 * ��ü�� �ѱ�� BR
 */
package com.forroom.suhyemin.kimbogyun.songmin.gcm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.WakefulBroadcastReceiver;

public class foroomGCMBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intentFromGCMServer) {

        ComponentName targetCompName = new ComponentName(context.getPackageName(),
                foroomGCMIntentService.class.getName());


        startWakefulService(context, intentFromGCMServer.setComponent(targetCompName));

    }
}