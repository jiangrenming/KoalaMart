package com.koalafield.cmart.bean.order;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/12.
 */

public class AttrsValues implements Serializable{
    private  String color;
    private String size;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
