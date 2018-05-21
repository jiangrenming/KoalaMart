package com.koalafield.cmart.utils;

import com.koalafield.cmart.bean.goods.GoodsItem;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/21
 * sku算法
 */

public class SelectSkuCalulator {


    /**
     * 选中颜色列表
     */
    public  static List<GoodsItem> selectCalutor(List<GoodsItem> items,int position,int states){

        for (int i = 0; i < items.size() ; i++) {
            GoodsItem goodsItem = items.get(i);
            if (i == position){
                goodsItem.setState(states);
            }else {
                if (goodsItem.getState() != 2){
                    goodsItem.setState(1);
                }
            }
            items.set(i, goodsItem);
        }
        return  items;

    }

    /**
     * 清空数据选择状态
     * @param mList
     * @return
     */
    public static List<GoodsItem> clearAdapterStates(List<GoodsItem> mList) {
        int size = mList.size();
        for (int i = 0; i < size; i++) {
            GoodsItem bean = mList.get(i);
            bean.setState(1);
            mList.set(i, bean);
        }
        return mList;
    }




}
