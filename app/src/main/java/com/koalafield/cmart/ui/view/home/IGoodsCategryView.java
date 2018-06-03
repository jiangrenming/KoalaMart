package com.koalafield.cmart.ui.view.home;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/15
 */

public interface IGoodsCategryView<T> extends IBaseView {

    void onGoodsCategrySucessFul(T data);
    void onGoodsCategryFailure(String message,int code);
}
