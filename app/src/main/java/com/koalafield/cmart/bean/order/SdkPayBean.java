package com.koalafield.cmart.bean.order;


import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/28.
 */

public class SdkPayBean implements Serializable {

    private  String TransactionNo;
    private String BillCode;
    private String Currency;
    private String Rate;
    private double PayPrice;
    private String PartnerId;
    private String AppId;
    private String TimeStamp;
    private String NonceStr;
    private String Package;
    private String SignType;
    private String PaySign;

    public String getBillCode() {
        return BillCode;
    }

    public void setBillCode(String billCode) {
        BillCode = billCode;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public double getPayPrice() {
        return PayPrice;
    }

    public void setPayPrice(double payPrice) {
        PayPrice = payPrice;
    }

    public String getPartnerId() {
        return PartnerId;
    }

    public void setPartnerId(String partnerId) {
        PartnerId = partnerId;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getNonceStr() {
        return NonceStr;
    }

    public void setNonceStr(String nonceStr) {
        NonceStr = nonceStr;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }

    public String getSignType() {
        return SignType;
    }

    public void setSignType(String signType) {
        SignType = signType;
    }

    public String getPaySign() {
        return PaySign;
    }

    public void setPaySign(String paySign) {
        PaySign = paySign;
    }

    public String getTransactionNo() {
        return TransactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        TransactionNo = transactionNo;
    }
}
