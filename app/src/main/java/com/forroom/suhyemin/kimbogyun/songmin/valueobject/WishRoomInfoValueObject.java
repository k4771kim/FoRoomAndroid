package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-01-29.
 */
public class WishRoomInfoValueObject {
    public String wr_info_profile;
    public String wr_info_nickname;
    public String wr_info_title;
    public String[] wr_info_hash;
    public String wr_info_houseImage;
    public String wr_info_replycount;
    public String wr_info_scrapcount;
    public String wr_info_userid;


    public String[] wr_info_gd_id;
    public String[] wr_info_gd_name;
    public String[] wr_info_gd_series;
    public String[] wr_info_gd_link;
    public String[] wr_info_gd_price;
    public String[] wr_info_gd_desc;
    public String[] wr_info_gd_sticker;
    public String[] wr_info_gd_scrapcount;
    public boolean[] wr_info_gd_scrapcheck;

    public WishRoomInfoValueObject(String wr_info_profile, String wr_info_nickname, String wr_info_title, String[] wr_info_hash, String wr_info_houseImage, String[] wr_info_gd_id, String[] wr_info_gd_name, String[] wr_info_gd_series, String[] wr_info_gd_link, String[] wr_info_gd_price, String[] wr_info_gd_desc, String[] wr_info_gd_sticker, String[] wr_info_gd_scrapcount) {
        this.wr_info_profile = wr_info_profile;
        this.wr_info_nickname = wr_info_nickname;
        this.wr_info_title = wr_info_title;
        this.wr_info_hash = wr_info_hash;
        this.wr_info_houseImage = wr_info_houseImage;
        this.wr_info_gd_id = wr_info_gd_id;
        this.wr_info_gd_name = wr_info_gd_name;
        this.wr_info_gd_series = wr_info_gd_series;
        this.wr_info_gd_link = wr_info_gd_link;
        this.wr_info_gd_price = wr_info_gd_price;
        this.wr_info_gd_desc = wr_info_gd_desc;
        this.wr_info_gd_sticker = wr_info_gd_sticker;
        this.wr_info_gd_scrapcount = wr_info_gd_scrapcount;
    }

    public WishRoomInfoValueObject(){}
}
