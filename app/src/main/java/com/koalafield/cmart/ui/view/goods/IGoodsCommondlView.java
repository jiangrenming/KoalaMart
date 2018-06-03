package com.koalafield.cmart.ui.view.goods;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public interface IGoodsCommondlView<T> extends IBaseView {

    void onGoodsCommondSucessFul(T data);
    void onGoodsCommondFailure(String message,int code);

}
