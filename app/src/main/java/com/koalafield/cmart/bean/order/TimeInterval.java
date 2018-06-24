package com.koalafield.cmart.bean.order;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class TimeInterval implements Serializable{

    private String StartTime;
    private String EndTime;
    private  String Price;
    private String TimeId;
    private String Currency;
    private String ShowText;

    public String getShowText() {
        return ShowText;
    }

    public void setShowText(String showText) {
        ShowText = showText;
    }

    public String getTimeId() {
        return TimeId;
    }

    public void setTimeId(String timeId) {
        TimeId = timeId;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
