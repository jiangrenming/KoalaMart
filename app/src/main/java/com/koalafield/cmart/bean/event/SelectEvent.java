package com.koalafield.cmart.bean.event;

/**
 * Created by jiangrenming on 2018/5/13.
 * 切换界面用的
 */

public class SelectEvent {


    public  String type;

    public  SelectEvent(String mType){
        this.type = mType;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
