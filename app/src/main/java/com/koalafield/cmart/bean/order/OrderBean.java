package com.koalafield.cmart.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/12.
 */

public class OrderBean  implements Serializable{

    private String orderNo ;
    private String statue;
    private String order_time;
    private  int orderCount;
    private  long totalAmount;
    private List<OrderItems> orderItem;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItems> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItems> orderItem) {
        this.orderItem = orderItem;
    }
}
