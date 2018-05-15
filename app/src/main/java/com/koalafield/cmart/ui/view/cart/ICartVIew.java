package com.koalafield.cmart.ui.view.cart;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/15.
 */

public interface ICartVIew<T> extends IBaseView {

    void onSucessNumberful(T data);
    void onNumberFailure(String message);




}
