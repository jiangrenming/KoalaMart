package com.koalafield.cmart.bean.user;

import java.io.Serializable;

/**
 *
 * @author jiangrenming
 * @date 2018/5/11
 */

public class AddressManagerBean implements Serializable{
    private  int Customerid;
    private String Country;
    private String Province;
    private String City;
    private String Area;
    private String Address;
    private String Number;
    private String Contactname;
    private String Contactphone;
    private String Contactid;
    private String Images;
    private String Zipcode;
    private String Createdtime;
    private String Lat;
    private String Lng;
    private int Id;
    private  boolean isSelected;

    public int getCustomerid() {
        return Customerid;
    }

    public void setCustomerid(int customerid) {
        Customerid = customerid;
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
        this.Province = province;
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

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
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

    public String getContactid() {
        return Contactid;
    }

    public void setContactid(String contactid) {
        Contactid = contactid;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }

    public String getCreatedtime() {
        return Createdtime;
    }

    public void setCreatedtime(String createdtime) {
        Createdtime = createdtime;
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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "AddressManagerBean{" +
                "Customerid=" + Customerid +
                ", Country='" + Country + '\'' +
                ", City='" + City + '\'' +
                ", Area='" + Area + '\'' +
                ", Address='" + Address + '\'' +
                ", Number='" + Number + '\'' +
                ", Contactname='" + Contactname + '\'' +
                ", Contactphone='" + Contactphone + '\'' +
                ", Contactid='" + Contactid + '\'' +
                ", Images='" + Images + '\'' +
                ", Zipcode='" + Zipcode + '\'' +
                ", Createdtime='" + Createdtime + '\'' +
                ", Lat='" + Lat + '\'' +
                ", Lng='" + Lng + '\'' +
                ", Id=" + Id +
                ", isSelected=" + isSelected +
                '}';
    }
}
