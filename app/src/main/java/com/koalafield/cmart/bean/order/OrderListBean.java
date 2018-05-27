package com.koalafield.cmart.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public class OrderListBean implements Serializable{

    private String BillNo;
    private String Currency;
    private int TotalCount;
    private  double DeliveryFree;
    private double TotalPrice;
    private double Tax;
    private  String StatusText;
    private String CreatedTime;
    List<OrderItemAttrs>  GoodsList;

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int totalCount) {
        TotalCount = totalCount;
    }

    public double getDeliveryFree() {
        return DeliveryFree;
    }

    public void setDeliveryFree(double deliveryFree) {
        DeliveryFree = deliveryFree;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public double getTax() {
        return Tax;
    }

    public void setTax(double tax) {
        Tax = tax;
    }

    public String getStatusText() {
        return StatusText;
    }

    public void setStatusText(String statusText) {
        StatusText = statusText;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public List<OrderItemAttrs> getGoodsList() {
        return GoodsList;
    }

    public void setGoodsList(List<OrderItemAttrs> goodsList) {
        GoodsList = goodsList;
    }

    @Override
    public String toString() {
        return "OrderListBean{" +
                "BillNo='" + BillNo + '\'' +
                ", Currency='" + Currency + '\'' +
                ", TotalCount=" + TotalCount +
                ", DeliveryFree=" + DeliveryFree +
                ", TotalPrice=" + TotalPrice +
                ", Tax=" + Tax +
                ", StatusText='" + StatusText + '\'' +
                ", CreatedTime='" + CreatedTime + '\'' +
                ", GoodsList=" + GoodsList +
                '}';
    }
}
