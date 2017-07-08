package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-01-27.
 */
public class MagazineValueObject {
    public String magazineImage;
    public String mg_title;
    public String mg_seriesNo;
    public String mg_subtitle;
    public String mg_id;

    public MagazineValueObject(){

    }

    public MagazineValueObject(String magazineImage, String mg_title, String mg_seriesNo, String mg_subtitle, String mg_id) {
        this.magazineImage = magazineImage;
        this.mg_title = mg_title;
        this.mg_seriesNo = mg_seriesNo;
        this.mg_subtitle = mg_subtitle;
        this.mg_id = mg_id;
    }
}


