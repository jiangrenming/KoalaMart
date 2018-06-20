package com.koalafield.cmart.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class Rule implements Serializable{


    private String Country;
    private String State;
    private  String Cost;
    private String Weight;
    private List<TimeInterval> TimeIntervalList;
    private String HintText;
    public String getHintText() {
        return HintText;
    }

    public void setHintText(String hintText) {
        HintText = hintText;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public List<TimeInterval> getTimeIntervalList() {
        return TimeIntervalList;
    }

    public void setTimeIntervalList(List<TimeInterval> timeIntervalList) {
        TimeIntervalList = timeIntervalList;
    }
}
