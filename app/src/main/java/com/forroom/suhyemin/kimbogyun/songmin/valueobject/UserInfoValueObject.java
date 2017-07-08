package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-02-12.
 */
public class UserInfoValueObject {
    public String usr_id;
    public String usr_way;
    public String usr_name;
    public String usr_likecount;
    public String usr_writecount;
    public String usr_pic;
    public String usr_desc;
    public String usr_thumb;
    public String usr_jjimcount;

    public UserInfoValueObject(){}

    public UserInfoValueObject(String usr_id, String usr_way, String usr_name, String usr_likecount, String usr_likewritecount, String usr_pic, String usr_desc, String usr_thumb) {
        this.usr_id = usr_id;
        this.usr_way = usr_way;
        this.usr_name = usr_name;
        this.usr_likecount = usr_likecount;
        this.usr_writecount = usr_likewritecount;
        this.usr_pic = usr_pic;
        this.usr_desc = usr_desc;
        this.usr_thumb = usr_thumb;
    }
}
