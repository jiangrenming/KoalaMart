package com.koalafield.cmart.bean.event;

/**
 * Created by jiangrenming on 2018/6/14.
 */

public class SkipEvent {

    public   int selectPosition;
    public SkipEvent(int position){
        this.selectPosition = position;
    }
}
