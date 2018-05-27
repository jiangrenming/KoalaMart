package com.koalafield.cmart.bean.user;

import com.koalafield.cmart.base.bean.SpecialResponseBean;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/24.
 */

public class DisCountBean implements Serializable {

    private String Code;
    private double Amount;
    private String Expire;
    private int Deduct;
    private String MinBillUseTotalPrice;

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public String getExpire() {
        return Expire;
    }

    public void setExpire(String expire) {
        Expire = expire;
    }

    public int getDeduct() {
        return Deduct;
    }

    public void setDeduct(int deduct) {
        Deduct = deduct;
    }

    public String getMinBillUseTotalPrice() {
        return MinBillUseTotalPrice;
    }

    public void setMinBillUseTotalPrice(String minBillUseTotalPrice) {
        MinBillUseTotalPrice = minBillUseTotalPrice;
    }

    @Override
    public String toString() {
        return "DisCountBean{" +
                "Code='" + Code + '\'' +
                ", Amount=" + Amount +
                ", Expire='" + Expire + '\'' +
                ", Deduct=" + Deduct +
                ", MinBillUseTotalPrice='" + MinBillUseTotalPrice + '\'' +
                '}';
    }
}