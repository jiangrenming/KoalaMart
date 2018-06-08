package com.koalafield.cmart.bean.categry;

import com.koalafield.cmart.bean.goods.GoodsItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class GoodsListBean implements Serializable{
    private String Name;
    private  int Id;
    private String CoverImg;
    private String ShortName;
    private String Currency;
    private double CurrentPrice;
    private boolean IsOpenSelection;

    private List<GoodsItem> ColorList;
    private List<GoodsItem> SizeList;
    private List<GoodsItem> MaterialList;
    private List<GoodsItem> TypeList;
    private List<GoodsItem> WeightList;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCoverImg() {
        return CoverImg;
    }

    public void setCoverImg(String coverImg) {
        CoverImg = coverImg;
    }

    public String getShortName() {
        return ShortName;
    }

    public void setShortName(String shortName) {
        ShortName = shortName;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public double getCurrentPrice() {
        return CurrentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        CurrentPrice = currentPrice;
    }

    public boolean isOpenSelection() {
        return IsOpenSelection;
    }

    public void setOpenSelection(boolean openSelection) {
        IsOpenSelection = openSelection;
    }

    public List<GoodsItem> getColorList() {
        return ColorList;
    }

    public void setColorList(List<GoodsItem> colorList) {
        ColorList = colorList;
    }

    public List<GoodsItem> getSizeList() {
        return SizeList;
    }

    public void setSizeList(List<GoodsItem> sizeList) {
        SizeList = sizeList;
    }

    public List<GoodsItem> getMaterialList() {
        return MaterialList;
    }

    public void setMaterialList(List<GoodsItem> materialList) {
        MaterialList = materialList;
    }

    public List<GoodsItem> getTypeList() {
        return TypeList;
    }

    public void setTypeList(List<GoodsItem> typeList) {
        TypeList = typeList;
    }

    public List<GoodsItem> getWeightList() {
        return WeightList;
    }

    public void setWeightList(List<GoodsItem> weightList) {
        WeightList = weightList;
    }
}
