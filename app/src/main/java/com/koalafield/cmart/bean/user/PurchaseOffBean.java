package com.koalafield.cmart.bean.user;

import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.base.bean.SpecialResponseBean;

/**
 *
 * @author jiangrenming
 * @date 2018/5/23
 */

public class PurchaseOffBean extends SpecialResponseBean {

    private  int Id;
    private String CoverImg;
    private  String Name;
    private String ShortName;
    private  String Currency;
    private String CurrentPrice;

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    @Override
    public String toString() {
        return "GoodsCollectionsBean{" +
                "Id=" + Id +
                ", CoverImg='" + CoverImg + '\'' +
                ", Name='" + Name + '\'' +
                ", ShortName='" + ShortName + '\'' +
                ", Currency='" + Currency + '\'' +
                ", CurrentPrice='" + CurrentPrice + '\'' +
                '}';
    }
}
