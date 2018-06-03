package com.koalafield.cmart.ui.view.goods;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public interface IGoodsCollectionView<T> extends IBaseView {

    void onGoodsCollectionsSucessFul(T data);
    void onGoodsCollectionsFailure(String message,int code);

}
