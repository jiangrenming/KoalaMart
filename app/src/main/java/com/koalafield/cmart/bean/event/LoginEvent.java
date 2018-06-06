package com.koalafield.cmart.bean.event;

/**
 * Created by jiangrenming on 2018/6/6.
 */

public class LoginEvent {

    public int mType;
    public  int userAggree;
    public  String mCode;

    public LoginEvent(int type, int userAggree) {
        this.mType = type;
        this.userAggree = userAggree;
    }
    public LoginEvent(int type, int userAggree,String code) {
        this.mType = type;
        this.userAggree = userAggree;
        this.mCode =code;
    }
}
