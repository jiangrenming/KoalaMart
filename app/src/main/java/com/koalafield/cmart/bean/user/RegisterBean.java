package com.koalafield.cmart.bean.user;

import com.koalafield.cmart.base.bean.BaseResponseBean;

/**
 *
 * @author jiangrenming
 * @date 2018/5/12
 */

public class RegisterBean extends BaseResponseBean {

    private String Ticket;

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }
}
