package com.koalafield.cmart.bean.search;

import com.koalafield.cmart.bean.goods.GoodsItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class SearchListBean implements Serializable{


    private int Id;
    private String CoverImg;
    private String Name;
    private String ShortName;
    private String Currency;
    private int Count;
    private String CurrentPrice;
    private String OriginalPrice;
    private String IsOpenSelection;
    private List<GoodsItem> ColorList;
    private List<GoodsItem> SizeList;
    private List<GoodsItem> MaterialList;
    private List<GoodsItem> TypeList;
    private List<GoodsItem> WeightList;

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getCurrentPrice() {
        return CurrentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        CurrentPrice = currentPrice;
    }

    public String getOriginalPrice() {
        return OriginalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        OriginalPrice = originalPrice;
    }

    public String getIsOpenSelection() {
        return IsOpenSelection;
    }

    public void setIsOpenSelection(String isOpenSelection) {
        IsOpenSelection = isOpenSelection;
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
