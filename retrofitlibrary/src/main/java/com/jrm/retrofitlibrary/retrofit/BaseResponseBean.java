package com.jrm.retrofitlibrary.retrofit;

import java.io.Serializable;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 */

public class BaseResponseBean<T> implements Serializable{

    private  int Code;
    private String Msg;
    private  T Data;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResponseBean{" +
                "Code=" + Code +
                ", Msg='" + Msg + '\'' +
                ", Data=" + Data +
                '}';
    }
}
