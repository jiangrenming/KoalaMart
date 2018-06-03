package com.koalafield.cmart.bean.cart;


import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/5/15.
 */

public class CartDataBean  implements Serializable {

    private int ContentId;
    private String Color;
    private String Size;
    private String Material;
    private String Weight;
    private String Type;
    private int Count;
    private CartItemBean Commodity;
    private  boolean isSelect;
    private  int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public CartItemBean getCommodity() {
        return Commodity;
    }

    public void setCommodity(CartItemBean commodity) {
        Commodity = commodity;
    }

    @Override
    public String toString() {
        return "CartDataBean{" +
                "ContentId=" + ContentId +
                ", Color='" + Color + '\'' +
                ", Size='" + Size + '\'' +
                ", Material='" + Material + '\'' +
                ", Weight='" + Weight + '\'' +
                ", Type='" + Type + '\'' +
                ", Count=" + Count +
                ", Commodity=" + Commodity +
                '}';
    }
}
