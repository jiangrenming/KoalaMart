package com.koalafield.cmart.bean.user;


import java.io.Serializable;

/**
 *
 * @author jiangrenming
 * @date 2018/5/12
 */

public class RegisterBean implements Serializable {

    private String Ticket;
    private String Phone;
    private String Nickname;
    private String Gender;
    private String Country;
    private String Avatar;

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }
}
