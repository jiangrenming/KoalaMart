package com.koalafield.cmart.ui.view.goods;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public interface IGoodsDetailView<T> extends IBaseView {

    void onGoodsDetailsSucessFul(T data);
    void onGoodsDetailsFailure(String message,int code);

}
