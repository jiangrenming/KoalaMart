package com.koalafield.cmart.bean.user;

import java.io.Serializable;

/**
 *
 * @author jiangrenming
 * @date 2018/5/11
 */

public class AddressManagerBean implements Serializable{
    private  int customerid;
    private String country;
    private String state;
    private String city;
    private String area;
    private String address;
    private String number;
    private String contactname;
    private String contactphone;
    private String contactid;
    private String images;
    private String zipcode;
    private String createdtime;
    private String lat;
    private String lng;
    private int Id;
    private  boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getContactphone() {
        return contactphone;
    }

    public void setContactphone(String contactphone) {
        this.contactphone = contactphone;
    }

    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @Override
    public String toString() {
        return "AddressManagerBean{" +
                "customerid=" + customerid +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", number='" + number + '\'' +
                ", contactname='" + contactname + '\'' +
                ", contactphone='" + contactphone + '\'' +
                ", contactid='" + contactid + '\'' +
                ", images='" + images + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", createdtime='" + createdtime + '\'' +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", Id=" + Id +
                '}';
    }
}
