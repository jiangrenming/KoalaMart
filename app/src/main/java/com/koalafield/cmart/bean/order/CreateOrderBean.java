package com.koalafield.cmart.bean.order;

import com.koalafield.cmart.base.bean.SpecialResponseBean;

/**
 * Created by jiangrenming on 2018/5/28.
 */

public class CreateOrderBean extends SpecialResponseBean {

    private  String OrderNo;

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }
}
