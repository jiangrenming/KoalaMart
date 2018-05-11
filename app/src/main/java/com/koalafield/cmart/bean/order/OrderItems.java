package com.koalafield.cmart.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/12.
 */

public class OrderItems implements Serializable{

    private String imgUrl;
    private String productName;
    private long amount;
    private String [] attrs;
    private int number;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String[] getAttrs() {
        return attrs;
    }

    public void setAttrs(String[] attrs) {
        this.attrs = attrs;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
