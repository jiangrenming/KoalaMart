package com.koalafield.cmart.ui.view.goods;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public interface IGoodsCollectionDeleteView<T> extends IBaseView {

    void onGoodsCollectionDelSucessFul(T data);
    void onGoodsCollectionDelFailure(String message);

}
