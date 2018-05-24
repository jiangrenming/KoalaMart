package com.koalafield.cmart.bean.home;

import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.base.bean.SpecialResponseBean;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/15
 * 首页分类商品数据
 */

public class GoodsCategryBean  implements Serializable {

    private List<GoodItemBean> GoodsList;
    private int CategoryId;
    private String ShowType;
    private String Img;
    private String Name;

    public List<GoodItemBean> getGoodsList() {
        return GoodsList;
    }

    public void setGoodsList(List<GoodItemBean> goodsList) {
        GoodsList = goodsList;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public String getShowType() {
        return ShowType;
    }

    public void setShowType(String showType) {
        ShowType = showType;
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

    @Override
    public String toString() {
        return "GoodsCategryBean{" +
                "GoodsList=" + GoodsList +
                ", CategoryId=" + CategoryId +
                ", ShowType='" + ShowType + '\'' +
                ", Img='" + Img + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}
