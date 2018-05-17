package com.koalafield.cmart.bean.cart;

import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.base.bean.SpecialResponseBean;

/**
 * Created by jiangrenming on 2018/5/15.
 */

public class CartNumberBean extends SpecialResponseBean {

    private  int Count;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
