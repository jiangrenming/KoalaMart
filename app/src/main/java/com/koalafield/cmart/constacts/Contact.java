package com.koalafield.cmart.constacts;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/6/3.
 */

public class Contact implements Serializable{

    private String mName;
    private int mType;

    public Contact(String name, int type) {
        mName = name;
        mType = type;
    }

    public String getmName() {
        return mName;
    }

    public int getmType() {
        return mType;
    }
}
