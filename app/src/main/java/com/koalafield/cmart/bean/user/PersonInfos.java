package com.koalafield.cmart.bean.user;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/6/4.
 */

public class PersonInfos implements Serializable{

    private String Phone;
    private String Nickname;
    private String Gender;
    private String Avatar;

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

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

}
