package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-02-10.
 */

public class PhotoValueObject {

    private String imgPath;
    private boolean selected = false;

    public PhotoValueObject(String imgPath, boolean selected) {
        this.imgPath = imgPath;
        this.selected = selected;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
