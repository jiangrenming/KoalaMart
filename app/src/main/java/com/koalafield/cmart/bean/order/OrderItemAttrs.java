package com.koalafield.cmart.bean.order;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public class OrderItemAttrs implements Serializable{

    private  String Color;
    private String Size;
    private  int ContentId;
    private  String Material;
    private String Weight;
    private String Type;
    private String Subject;
    private String CoverImg;
    private double Price;
    private int Count;

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

    public int getContentId() {
        return ContentId;
    }

    public void setContentId(int contentId) {
        ContentId = contentId;
    }

    public String getMaterial() {
        return Material;
    }

    public void setMaterial(String material) {
        Material = material;
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getCoverImg() {
        return CoverImg;
    }

    public void setCoverImg(String coverImg) {
        CoverImg = coverImg;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    @Override
    public String toString() {
        return "OrderItemAttrs{" +
                "Color='" + Color + '\'' +
                ", Size='" + Size + '\'' +
                ", ContentId=" + ContentId +
                ", Material='" + Material + '\'' +
                ", Weight='" + Weight + '\'' +
                ", Type='" + Type + '\'' +
                ", Subject='" + Subject + '\'' +
                ", CoverImg='" + CoverImg + '\'' +
                ", Price=" + Price +
                ", Count=" + Count +
                '}';
    }
}
