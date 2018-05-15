package com.koalafield.cmart.bean.event;

/**
 * Created by jiangrenming on 2018/5/16.
 */

public class CartEvent {
    public  String type;

    public  CartEvent(String mType){
        this.type = mType;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
