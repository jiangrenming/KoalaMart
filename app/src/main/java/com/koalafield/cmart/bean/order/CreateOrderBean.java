package com.koalafield.cmart.bean.order;


import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/28.
 */

public class CreateOrderBean implements Serializable {

    private  String OrderNo;

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }
}
