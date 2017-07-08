package com.forroom.suhyemin.kimbogyun.songmin.profilePage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.forroom.suhyemin.kimbogyun.songmin.FoRoomLoginActivity;
import com.forroom.suhyemin.kimbogyun.songmin.ForRoomApplication;
import com.forroom.suhyemin.kimbogyun.songmin.MainActivity;
import com.forroom.suhyemin.kimbogyun.songmin.common.PropertyManager;
import com.forroom.suhyemin.kimbogyun.songmin.R;

/**
 * Created by KBG on 2016-03-04.
 */
public class ConfigActivity extends Activity {

    private ListView configListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_config);

        ImageButton backBtn = (ImageButton) findViewById(R.id.config_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        configListView = (ListView) findViewById(R.id.configListView);

        configListViewAdatper adapter = new configListViewAdatper();
        configListView.setAdapter(adapter);
        configListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (position == 3) {
                    PropertyManager.getInstance().setID("");
                    PropertyManager.getInstance().setPassword("");
                    PropertyManager.getInstance().setWay("");
                    Intent intent = new Intent(ConfigActivity.this, FoRoomLoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    ProfileActivity.mActivity.finish();
                    MainActivity.mActivity.finish();
                    startActivity(intent);
                    finish();
                } else if (position == 0) {
                    if (PropertyManager.getInstance().getPushSwitch().equalsIgnoreCase("true")) {
                        pushOnOffSwitch.setImageResource(R.drawable.config_off);
                        PropertyManager.getInstance().setPushSwitch("false");
                    } else {
                        pushOnOffSwitch.setImageResource(R.drawable.config_on);
                        PropertyManager.getInstance().setPushSwitch("true");
                    }

                }else if (position == 2){
                    Toast.makeText(ConfigActivity.this, ForRoomApplication.enableUpdate, Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(ConfigActivity.this, "준비중입니다", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    ImageView pushOnOffSwitch;


    public class configListViewAdatper extends BaseAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public Object getItem(int position) {
            return configText[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        String[] configText = {"알림 설정", "고객 문의", "버전 정보", "로그아웃"};

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            configItemView v = new configItemView(getApplicationContext());
            v.setItemData(configText[position]);
            if (position == 0) {      //알림설정
                pushOnOffSwitch = new ImageView(ConfigActivity.this);

                if (PropertyManager.getInstance().getPushSwitch().equalsIgnoreCase("true"))
                    pushOnOffSwitch.setImageResource(R.drawable.config_on);
                else
                    pushOnOffSwitch.setImageResource(R.drawable.config_off);


                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                params.rightMargin = 75;
                pushOnOffSwitch.setLayoutParams(params);
                v.addView(pushOnOffSwitch);
            }
            else if (position == 2){
                TextView version = new TextView(ConfigActivity.this);

                version.setText("ver ." + ForRoomApplication.appVersion);
                version.setTextColor(Color.GRAY);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.addRule(RelativeLayout.CENTER_VERTICAL);
                params.rightMargin = 75;
                version.setLayoutParams(params);
                v.addView(version);


            }
            return v;
        }
    }

    public class configItemView extends RelativeLayout {
        TextView configTextView;

        public configItemView(Context context) {
            super(context);
            LayoutInflater.from(getContext()).inflate(R.layout.item_config, this);
            configTextView = (TextView) findViewById(R.id.item_config_text);
        }

        public void setItemData(String data) {
            configTextView.setText(data);
        }
    }

}
