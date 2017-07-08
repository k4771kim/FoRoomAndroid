package com.forroom.suhyemin.kimbogyun.songmin.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.forroom.suhyemin.kimbogyun.songmin.Login_Auth_Activity;
import com.forroom.suhyemin.kimbogyun.songmin.common.PropertyManager;
import com.forroom.suhyemin.kimbogyun.songmin.R;

/**
 * Created by KBG on 2016-03-05.
 */

public class goMarketDialog extends Dialog {

    Activity mActivity;
    String mWay;

    public goMarketDialog(Activity activity, String way) {
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
                PropertyManager.getInstance().setWay(mWay);
                Intent intent = new Intent(mActivity, Login_Auth_Activity.class);
                intent.putExtra("login_way", mWay);
                mActivity.startActivity(intent);
                mActivity.finish();
            }
        });


    }

    public goMarketDialog(Activity activity, final String email, final String password, String way) {
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
                PropertyManager.getInstance().setEmail(email);
                PropertyManager.getInstance().setPassword(password);
                PropertyManager.getInstance().setWay(mWay);
                Intent intent = new Intent(mActivity, Login_Auth_Activity.class);
                intent.putExtra("login_way", mWay);
                mActivity.startActivity(intent);
                mActivity.finish();
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public goMarketDialog(Context context) {
        super(context);


    }

}

