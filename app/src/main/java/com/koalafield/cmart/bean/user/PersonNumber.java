package com.koalafield.cmart.bean.user;

import com.koalafield.cmart.base.bean.SpecialResponseBean;

/**
 * Created by jiangrenming on 2018/5/23.
 */

public class PersonNumber extends SpecialResponseBean {


    private  int FollowCount;
    private  int ScoreCount;
    private  int CouponCount;

    public int getFollowCount() {
        return FollowCount;
    }

    public void setFollowCount(int followCount) {
        FollowCount = followCount;
    }

    public int getScoreCount() {
        return ScoreCount;
    }

    public void setScoreCount(int scoreCount) {
        ScoreCount = scoreCount;
    }

    public int getCouponCount() {
        return CouponCount;
    }

    public void setCouponCount(int couponCount) {
        CouponCount = couponCount;
    }

    @Override
    public String toString() {
        return "PersonNumber{" +
                "FollowCount=" + FollowCount +
                ", ScoreCount=" + ScoreCount +
                ", CouponCount=" + CouponCount +
                '}';
    }
}
