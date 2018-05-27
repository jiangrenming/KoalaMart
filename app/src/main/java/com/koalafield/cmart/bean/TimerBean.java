package com.koalafield.cmart.bean;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class TimerBean implements Serializable{

    private int Id;
    private boolean isSelect;
    private  String date;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
