package com.koalafield.cmart.bean.user;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/6/19.
 */

public class ShareDataBean implements Serializable{

    private String Name;
    private String Value;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
