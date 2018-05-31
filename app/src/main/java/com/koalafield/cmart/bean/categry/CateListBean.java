package com.koalafield.cmart.bean.categry;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class CateListBean implements Serializable{

    private int Id;
    private String Name;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
