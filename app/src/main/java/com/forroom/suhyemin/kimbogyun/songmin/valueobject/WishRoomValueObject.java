package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-01-27.
 */
public class WishRoomValueObject {
    public String nickname;
    public String title;
    public String[] hash;
    public String comment_text;
    public String share_text;
    public String like_text;
    public String hit;
    public String date;
    public String id;

    public String wishhouse;
    public String color1;
    public String color2;
    public String color3;
    public boolean wr_scrap_check = false;

    public WishRoomValueObject(boolean scrap_check,String nickname, String title, String[] hash, String comment_text, String share_text, String like_text, String hit,String wishhouse, String  id, String date, String color1, String color2, String color3) {
        this.nickname = nickname;
        this.title = title;
        this.wr_scrap_check = scrap_check;
        this.hash = hash;
        this.comment_text = comment_text;
        this.share_text = share_text;
        this.like_text = like_text;
        this.wishhouse = wishhouse;
        this.hit = hit;
        this.date = date;
        this.id = id;
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
    }

    public WishRoomValueObject(){

    }
}


