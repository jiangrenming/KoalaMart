package com.koalafield.cmart.bean.categry;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/14
 * 二级分类
 */

public class CategryTwoBean implements Serializable{

    private String[] ChildList;
    private int Id;
    private String Img;
    private  String Name;

    public String[] getChildList() {
        return ChildList;
    }

    public void setChildList(String[] childList) {
        ChildList = childList;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
