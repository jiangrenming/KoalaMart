package com.koalafield.cmart.bean.user;

import java.io.Serializable;

/**
 *
 * @author jiangrenming
 * @date 2018/5/11
 */

public class AddressManagerBean implements Serializable{

    private String name;
    private String address;
    private String phone;
    private Boolean isCheck;  //是否设为默认
    private  int addressId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }


}
