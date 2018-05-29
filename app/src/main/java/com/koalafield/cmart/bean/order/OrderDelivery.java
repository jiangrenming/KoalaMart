package com.koalafield.cmart.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/29.
 */

public class OrderDelivery implements Serializable{

    private int Id;
    private List<Rule> RuleList;
    private String Icon;
    private  String DeliveryName;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public List<Rule> getRuleList() {
        return RuleList;
    }

    public void setRuleList(List<Rule> ruleList) {
        RuleList = ruleList;
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
}
