package com.koalafield.cmart.bean.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/6/21.
 */

public class LeftTimer implements Serializable{

    private String DateStr;
    private List<TimeInterval> TimeList;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getDateStr() {
        return DateStr;
    }

    public void setDateStr(String dateStr) {
        DateStr = dateStr;
    }

    public List<TimeInterval> getTimeList() {
        return TimeList;
    }

    public void setTimeList(List<TimeInterval> timeList) {
        TimeList = timeList;
    }
}
