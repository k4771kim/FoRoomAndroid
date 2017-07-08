package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-01-28.
 */
public class MyRoomValueObject {
    public String nickname;
    public String title;
    public String comment_text;
    public String share_text;
    public String like_text;
    public String date;
    public String id;

    public String[] hash;
    public String[] myhouse;

    public boolean mr_scrap_check;

    public MyRoomValueObject(boolean scrap_check,String[] myhouse, String title, String[] hash, String comment_text, String date, String id,String share_text, String like_text, String nickname) {
        this.myhouse = myhouse;
        this.title = title;
        this.mr_scrap_check = scrap_check;
        this.hash = hash;
        this.comment_text = comment_text;
        this.share_text = share_text;
        this.like_text = like_text;
        this.nickname = nickname;
        this.date = date;
        this.id = id;
    }

    public MyRoomValueObject(){
    }
}
