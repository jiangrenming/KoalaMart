package com.koalafield.cmart.bean.categry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class CateBrandGoodsListBean implements Serializable{
    private List<BrandListBean> BrandList;
    private List<CateListBean> CateList;
    private List<GoodsListBean> GoodsList;

    public List<BrandListBean> getBrandList() {
        return BrandList;
    }

    public void setBrandList(List<BrandListBean> brandList) {
        BrandList = brandList;
    }

    public List<CateListBean> getCateList() {
        return CateList;
    }

    public void setCateList(List<CateListBean> cateList) {
        CateList = cateList;
    }

    public List<GoodsListBean> getGoodsList() {
        return GoodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        GoodsList = goodsList;
    }
}
