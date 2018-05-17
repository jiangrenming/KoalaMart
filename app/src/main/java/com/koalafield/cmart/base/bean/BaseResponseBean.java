package com.koalafield.cmart.base.bean;

import java.io.Serializable;

/**
 *
 * @author jiangrenming
 * @date 2018/5/9
 */

public class BaseResponseBean implements Serializable{

    private  int Code;
    private String Msg;

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


    //   private  T Data;

   /* public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }*/

   /* @Override
    public int getCode() {
        return Code;
    }

    @Override
    public void setCode(int code) {
        Code = code;
    }

    @Override
    public String getMsg() {
        return Msg;
    }

    @Override
    public void setMsg(String msg) {
        Msg = msg;
    }*/
}
