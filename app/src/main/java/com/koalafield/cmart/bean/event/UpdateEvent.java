package com.koalafield.cmart.bean.event;

/**
 * Created by jiangrenming on 2018/6/4.
 */

public class UpdateEvent {

    public String mType;
    public  long mTime;

    public UpdateEvent(String type,long time) {
        this.mType = type;
        this.mTime = time;
    }

}
