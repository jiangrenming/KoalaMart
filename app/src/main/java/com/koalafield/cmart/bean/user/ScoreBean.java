package com.koalafield.cmart.bean.user;

import com.koalafield.cmart.base.bean.SpecialResponseBean;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/24.
 */

public class ScoreBean  implements Serializable {

    private String CreatedTime;
    private int Score;
    private String Remark;

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    @Override
    public String toString() {
        return "ScoreBean{" +
                "CreatedTime='" + CreatedTime + '\'' +
                ", Score=" + Score +
                ", Remark='" + Remark + '\'' +
                '}';
    }
}
