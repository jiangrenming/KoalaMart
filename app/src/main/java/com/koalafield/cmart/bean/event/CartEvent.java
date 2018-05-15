package com.koalafield.cmart.bean.event;

/**
 * Created by jiangrenming on 2018/5/16.
 */

public class CartEvent {
    public   int nCount;
    public  int  type ;

    public CartEvent(int count,int type) {
        this.nCount = count;
        this.type = type;
    }
}
