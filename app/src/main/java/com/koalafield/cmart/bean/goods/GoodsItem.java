package com.koalafield.cmart.bean.goods;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/18.
 */

public class GoodsItem implements Serializable{

    private String Name;
    private String RaisePrice;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRaisePrice() {
        return RaisePrice;
    }

    public void setRaisePrice(String raisePrice) {
        RaisePrice = raisePrice;
    }
}
