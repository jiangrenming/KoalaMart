package com.koalafield.cmart.bean.categry;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class GoodsListBean implements Serializable{
    private String Name;
    private  int Id;
    private String CoverImg;
    private String ShortName;
    private String Currency;
    private String CurrentPrice;
    private boolean IsOpenSelection;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCoverImg() {
        return CoverImg;
    }

    public void setCoverImg(String coverImg) {
        CoverImg = coverImg;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getCurrentPrice() {
        return CurrentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        CurrentPrice = currentPrice;
    }

    public boolean isOpenSelection() {
        return IsOpenSelection;
    }

    public void setOpenSelection(boolean openSelection) {
        IsOpenSelection = openSelection;
    }
}
