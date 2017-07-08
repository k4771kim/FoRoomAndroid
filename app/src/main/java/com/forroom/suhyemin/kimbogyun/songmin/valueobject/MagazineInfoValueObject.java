package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-02-05.
 */
public class MagazineInfoValueObject {
    public String[] mgz_info_pic;
    public String mgz_info_title;
    public String mgz_info_series;
    public String mgz_info_id;
    public String[] mgz_info_text;
    public String mgz_info_replycount;
    public String mgz_info_scrap_count;
    public String mgz_info_date;
    public boolean mgz_info_usrvr;
    public boolean mgz_info_scrapcheck;
    public String mgz_vr_pic;

    public MagazineInfoValueObject(){}

    public MagazineInfoValueObject(String mgz_date, boolean mgz_vr,String[] mgz_info_pic, String mgz_info_title, String mgz_info_series, String mgz_info_id, String[] mgz_info_text, String mgz_info_replycount, String mgz_info_scrap_count) {
        this.mgz_info_pic = mgz_info_pic;
        this.mgz_info_date = mgz_date;
        this.mgz_info_usrvr = mgz_vr;
        this.mgz_info_title = mgz_info_title;
        this.mgz_info_series = mgz_info_series;
        this.mgz_info_id = mgz_info_id;
        this.mgz_info_text = mgz_info_text;
        this.mgz_info_replycount = mgz_info_replycount;
        this.mgz_info_scrap_count = mgz_info_scrap_count;
    }
}
