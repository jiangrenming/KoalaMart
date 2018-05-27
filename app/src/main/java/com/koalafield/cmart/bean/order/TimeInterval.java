package com.koalafield.cmart.bean.order;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class TimeInterval implements Serializable{

    private String StartTime;
    private String EndTime;
    private  String Raise;

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

    public String getRaise() {
        return Raise;
    }

    public void setRaise(String raise) {
        Raise = raise;
    }
}
