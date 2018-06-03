package com.koalafield.cmart.bean.user;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/6/1.
 */

public class CountryCode implements Serializable{

    public String Letter;
    public String Country;
    public String CodeStr;
    private int mType;

    public CountryCode(String Country,int type){
        this.mType = type;
        this.Country = Country;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public CountryCode(String name) {
        this.Country = name;
    }

    public String getLetter() {
        return Letter;
    }

    public void setLetter(String letter) {
        Letter = letter;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCodeStr() {
        return CodeStr;
    }

    public void setCodeStr(String codeStr) {
        CodeStr = codeStr;
    }
}
