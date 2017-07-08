package com.forroom.suhyemin.kimbogyun.songmin.valueobject;

/**
 * Created by ccei on 2016-02-11.
 */
public class PictagValueObject {
    public double[] dataX;
    public double[] dataY;
    public String[] Text;
    public int[] Color;

    public PictagValueObject(){}

    public PictagValueObject(double[] dataX, double[] dataY, String[] text, int[] color) {
        this.dataX = dataX;
        this.dataY = dataY;
        Text = text;
        Color = color;
    }
}
