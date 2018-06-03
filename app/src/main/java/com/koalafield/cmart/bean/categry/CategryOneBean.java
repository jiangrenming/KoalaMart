package com.koalafield.cmart.bean.categry;


import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/14
 */

public class CategryOneBean implements Serializable{



    private int Id;
    private String Img;
    private String Name;
    private boolean  isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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
