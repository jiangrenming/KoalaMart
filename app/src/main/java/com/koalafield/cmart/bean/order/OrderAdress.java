package com.koalafield.cmart.bean.order;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/29.
 */

public class OrderAdress implements Serializable{
    private  int Id;
    private String Country;
    private String Province;
    private  String City;
    private  String Area;
    private  String Address;
    private  String Contactname;
    private String Contactphone;
    private  String Zipcode;
    private String Lat;
    private  String Lng;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactname() {
        return Contactname;
    }

    public void setContactname(String contactname) {
        Contactname = contactname;
    }

    public String getContactphone() {
        return Contactphone;
    }

    public void setContactphone(String contactphone) {
        Contactphone = contactphone;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String lng) {
        Lng = lng;
    }
}
