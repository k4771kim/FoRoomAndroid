package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-02-17.
 */
public class GoodsItemInfoValueObject {
    public String goods_name;
    public String goods_id;
    public String goods_desc;
    public String goods_image;
    public String goods_price;
    public boolean goods_scrap_check;
    public String goods_scrap_count;
    public String goods_series;

    public GoodsItemInfoValueObject(){}

    public GoodsItemInfoValueObject(String goods_name, String goods_id, String goods_desc, String goods_image, String goods_price, boolean goods_scrap_check, String goods_scrap_count) {
        this.goods_name = goods_name;
        this.goods_id = goods_id;
        this.goods_desc = goods_desc;
        this.goods_image = goods_image;
        this.goods_price = goods_price;
        this.goods_scrap_check = goods_scrap_check;
        this.goods_scrap_count = goods_scrap_count;
    }
}
