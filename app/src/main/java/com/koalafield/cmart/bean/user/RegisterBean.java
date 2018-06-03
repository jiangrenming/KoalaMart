package com.koalafield.cmart.bean.user;


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
