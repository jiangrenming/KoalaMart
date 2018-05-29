package com.koalafield.cmart.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/29.
 */

public class OrderDetailsInfo implements Serializable{

    private String BillNo;
    private String TotalWeight;
    private String Currency;

    private String StatusText;
    private int Status;
    private int PaymentId;

    private String PaymentName;
    private String PayTime;
    private String CreatedTime;
    private String ExpirePayTimeStamp;
    private String BookDeliveryTime;

    private List<OrderItemAttrs> GoodsList;

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public String getTotalWeight() {
        return TotalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        TotalWeight = totalWeight;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getStatusText() {
        return StatusText;
    }

    public void setStatusText(String statusText) {
        StatusText = statusText;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(int paymentId) {
        PaymentId = paymentId;
    }

    public String getPaymentName() {
        return PaymentName;
    }

    public void setPaymentName(String paymentName) {
        PaymentName = paymentName;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String payTime) {
        PayTime = payTime;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public String getExpirePayTimeStamp() {
        return ExpirePayTimeStamp;
    }

    public void setExpirePayTimeStamp(String expirePayTimeStamp) {
        ExpirePayTimeStamp = expirePayTimeStamp;
    }

    public String getBookDeliveryTime() {
        return BookDeliveryTime;
    }

    public void setBookDeliveryTime(String bookDeliveryTime) {
        BookDeliveryTime = bookDeliveryTime;
    }

    public List<OrderItemAttrs> getGoodsList() {
        return GoodsList;
    }

    public void setGoodsList(List<OrderItemAttrs> goodsList) {
        GoodsList = goodsList;
    }
}
