package com.koalafield.cmart.bean.order;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class ShoppingCart implements Serializable{

    private  int Id;
    private  int ContentId;
    private  String Color;
    private  String Size;
    private  String Weight;
    private  String Material;
    private  String Type;
    private   int Count;
    private   CommodityBean Commodity;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getContentId() {
        return ContentId;
    }

    public void setContentId(int contentId) {
        ContentId = contentId;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public CommodityBean getCommodity() {
        return Commodity;
    }

    public void setCommodity(CommodityBean commodity) {
        Commodity = commodity;
    }
}
