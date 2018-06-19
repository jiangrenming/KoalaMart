package com.koalafield.cmart.bean.user;

import java.io.Serializable;

/**
 *
 * @author jiangrenming
 * @date 2018/6/19
 */

public class AvtorBean implements Serializable{

    private String Phone;
    private String Nickname;
    private String Gender;
    private String Country;
    private String Avatar;
    private String InviteCode;

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

    public String getInviteCode() {
        return InviteCode;
    }

    public void setInviteCode(String inviteCode) {
        InviteCode = inviteCode;
    }

    @Override
    public String toString() {
        return "AvtorBean{" +
                "Phone='" + Phone + '\'' +
                ", Nickname='" + Nickname + '\'' +
                ", Gender='" + Gender + '\'' +
                ", Country='" + Country + '\'' +
                ", Avatar='" + Avatar + '\'' +
                ", InviteCode='" + InviteCode + '\'' +
                '}';
    }
}
