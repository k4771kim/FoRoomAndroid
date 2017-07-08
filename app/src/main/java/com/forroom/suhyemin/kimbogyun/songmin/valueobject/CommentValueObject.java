package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-02-03.
 */
public class CommentValueObject {
    public String rp_profile;
    public String rp_nickname;
    public String rp_text;
    public String rp_time;

    public CommentValueObject(){}

    public CommentValueObject(String rp_profile, String rp_nickname, String rp_text, String rp_time) {
        this.rp_profile = rp_profile;
        this.rp_nickname = rp_nickname;
        this.rp_text = rp_text;
        this.rp_time = rp_time;
    }
}
