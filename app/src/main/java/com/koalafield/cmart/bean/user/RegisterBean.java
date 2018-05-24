package com.koalafield.cmart.bean.user;

import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.base.bean.SpecialResponseBean;

import java.io.Serializable;

/**
 *
 * @author jiangrenming
 * @date 2018/5/12
 */

public class RegisterBean implements Serializable {

    private String Ticket;

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }
}
