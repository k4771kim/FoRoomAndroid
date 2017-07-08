package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

import java.io.File;

/**
 * Created by ccei on 2016-02-12.
 */
public class ProfileWriteValueObject {
    public String profile_id;
    public String profile_nickname;
    public String profile_desc;
    public File profile_image;

    public ProfileWriteValueObject(){}

    public ProfileWriteValueObject(String profile_id,String profile_nickname, String profile_desc, File profile_image) {
        this.profile_nickname = profile_nickname;
        this.profile_id = profile_id;
        this.profile_desc = profile_desc;
        this.profile_image = profile_image;
    }
}
