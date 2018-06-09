package com.koalafield.cmart.bean.event;

import com.koalafield.cmart.bean.user.RegisterBean;

/**
 * Created by jiangrenming on 2018/6/9.
 */

public class WxEvent {

    public RegisterBean mRegister;

    public WxEvent(RegisterBean register){
        this.mRegister = register;
    }

}
