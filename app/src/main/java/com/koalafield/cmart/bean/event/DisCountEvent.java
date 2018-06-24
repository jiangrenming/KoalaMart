package com.koalafield.cmart.bean.event;

import com.koalafield.cmart.bean.user.DisCountBean;

/**
 * Created by jiangrenming on 2018/6/24.
 */

public class DisCountEvent {

    public DisCountBean mDisCountBean;

    public DisCountEvent(DisCountBean disCountBean) {
        this.mDisCountBean =disCountBean;
    }


}
