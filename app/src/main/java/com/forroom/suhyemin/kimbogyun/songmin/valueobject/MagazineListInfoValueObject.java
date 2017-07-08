package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-02-20.
 */
public class MagazineListInfoValueObject {
    public String mgz_image;
    public String mgz_series;
    public String titile;
    public String mgz_id;
    public String mgz_date;

    public MagazineListInfoValueObject(){}

    public MagazineListInfoValueObject(String mgz_image, String mgz_series, String titile, String mgz_id, String mgz_date) {
        this.mgz_image = mgz_image;
        this.mgz_series = mgz_series;
        this.titile = titile;
        this.mgz_id = mgz_id;
        this.mgz_date = mgz_date;
    }
}
