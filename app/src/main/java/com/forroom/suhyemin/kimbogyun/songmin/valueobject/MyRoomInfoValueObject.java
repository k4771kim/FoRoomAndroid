package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

import java.util.ArrayList;

/**
 * Created by ccei on 2016-01-29.
 */
public class MyRoomInfoValueObject {

    public String mr_info_profile;
    public String mr_info_nickname;
    public String mr_info_title;
    public String[] mr_info_hash;
    public String mr_info_date;
    public String mr_info_reply_text;
    public String mr_info_scrap_text;
    public String mr_info_hit;
    public String mr_info_userid;
    public String[] mr_info_myroomimage;
    public String[] mr_info_myroomtext;
//    public ArrayList<PictagValueObject> pictag = new ArrayList<>();

    public MyRoomInfoValueObject(String mr_info_profile, String mr_info_nickname, String mr_info_title, String[] mr_info_hash, String mr_info_date, String mr_info_reply_text, String mr_info_scrap_text, String mr_info_hit, String[] mr_info_myroomimage, String[] mr_info_myroomtext) {
        this.mr_info_profile = mr_info_profile;
        this.mr_info_nickname = mr_info_nickname;
        this.mr_info_title = mr_info_title;
        this.mr_info_hash = mr_info_hash;
        this.mr_info_date = mr_info_date;
        this.mr_info_reply_text = mr_info_reply_text;
        this.mr_info_scrap_text = mr_info_scrap_text;
        this.mr_info_hit = mr_info_hit;
        this.mr_info_myroomimage = mr_info_myroomimage;
        this.mr_info_myroomtext = mr_info_myroomtext;
    }

    public MyRoomInfoValueObject(){}
}
