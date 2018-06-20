package com.koalafield.cmart.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public class Delivery implements Serializable{

    private int Id;
    private String Icon;
    private String DeliveryName;

    private List<Rule> RuleList;
    private  boolean isTypeSelect;


    public boolean isTypeSelect() {
        return isTypeSelect;
    }

    public void setTypeSelect(boolean typeSelect) {
        isTypeSelect = typeSelect;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public String getDeliveryName() {
        return DeliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        DeliveryName = deliveryName;
    }

    public List<Rule> getRuleList() {
        return RuleList;
    }

    public void setRuleList(List<Rule> ruleList) {
        RuleList = ruleList;
    }
}
