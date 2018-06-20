package com.koalafield.cmart.bean.order;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class OrderPrice implements Serializable{


    private double Rate;
    private double DeliveryPrice;
    private double ConsumptionTax;
    private double CouponPrice;
    private  double TotalGrabPrice;
    private double  TotalPriceAfterDiscount;
    private  double TotalPrice;
    private String Currency;
    private double TotalGoodsPrice;
    private  double ScoreDiscountPrice;

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public double getTotalGoodsPrice() {
        return TotalGoodsPrice;
    }

    public void setTotalGoodsPrice(double totalGoodsPrice) {
        TotalGoodsPrice = totalGoodsPrice;
    }

    public double getScoreDiscountPrice() {
        return ScoreDiscountPrice;
    }

    public void setScoreDiscountPrice(double scoreDiscountPrice) {
        ScoreDiscountPrice = scoreDiscountPrice;
    }

    public double getRate() {
        return Rate;
    }

    public void setRate(double rate) {
        Rate = rate;
    }

    public double getDeliveryPrice() {
        return DeliveryPrice;
    }

    public void setDeliveryPrice(double deliveryPrice) {
        DeliveryPrice = deliveryPrice;
    }

    public double getConsumptionTax() {
        return ConsumptionTax;
    }

    public void setConsumptionTax(double consumptionTax) {
        ConsumptionTax = consumptionTax;
    }

    public double getCouponPrice() {
        return CouponPrice;
    }

    public void setCouponPrice(double couponPrice) {
        CouponPrice = couponPrice;
    }

    public double getTotalGrabPrice() {
        return TotalGrabPrice;
    }

    public void setTotalGrabPrice(double totalGrabPrice) {
        TotalGrabPrice = totalGrabPrice;
    }

    public double getTotalPriceAfterDiscount() {
        return TotalPriceAfterDiscount;
    }

    public void setTotalPriceAfterDiscount(double totalPriceAfterDiscount) {
        TotalPriceAfterDiscount = totalPriceAfterDiscount;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderPrice{" +
                "Rate=" + Rate +
                ", DeliveryPrice=" + DeliveryPrice +
                ", ConsumptionTax=" + ConsumptionTax +
                ", CouponPrice=" + CouponPrice +
                ", TotalGrabPrice=" + TotalGrabPrice +
                ", TotalPriceAfterDiscount=" + TotalPriceAfterDiscount +
                ", TotalPrice=" + TotalPrice +
                '}';
    }
}
