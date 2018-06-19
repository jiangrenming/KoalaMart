package com.koalafield.cmart.bean.user;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/6/19.
 */

public class ShareBean implements Serializable{

    private String Img;
    private String Text1;
    private String Text2;
    private List<ShareDataBean> ItemList;

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getText1() {
        return Text1;
    }

    public void setText1(String text1) {
        Text1 = text1;
    }

    public String getText2() {
        return Text2;
    }

    public void setText2(String text2) {
        Text2 = text2;
    }

    public List<ShareDataBean> getItemList() {
        return ItemList;
    }

    public void setItemList(List<ShareDataBean> itemList) {
        ItemList = itemList;
    }
}
