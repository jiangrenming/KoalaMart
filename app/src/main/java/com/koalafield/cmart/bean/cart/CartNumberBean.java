package com.koalafield.cmart.bean.cart;


import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/15.
 */

public class CartNumberBean  implements Serializable {

    private  int Count;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
