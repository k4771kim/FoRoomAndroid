package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-02-17.
 */
public class LoginValueObject {
    public String userid;
    public String name;
    public String way;
    public String pwd;
    public String goodversion;

    public LoginValueObject(){}

    public LoginValueObject(String userid, String name, String way, String pwd) {
        this.userid = userid;
        this.name = name;
        this.way = way;
        this.pwd = pwd;
    }
}
