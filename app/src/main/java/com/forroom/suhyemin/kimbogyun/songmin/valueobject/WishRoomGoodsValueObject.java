package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-02-03.
 */
public class WishRoomGoodsValueObject {
    public String gd_id;
    public String gd_name;
    public String gd_series;
    public String gd_link;
    public String gd_price;
    public String gd_desc;
    public String gd_sticker;

    public WishRoomGoodsValueObject(){}

    public WishRoomGoodsValueObject(String gd_id, String gd_name, String gd_series, String gd_link, String gd_price, String gd_desc, String gd_sticker) {
        this.gd_id = gd_id;
        this.gd_name = gd_name;
        this.gd_series = gd_series;
        this.gd_link = gd_link;
        this.gd_price = gd_price;
        this.gd_desc = gd_desc;
        this.gd_sticker = gd_sticker;
    }
}
