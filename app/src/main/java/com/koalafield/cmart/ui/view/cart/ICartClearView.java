package com.koalafield.cmart.ui.view.cart;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/15.
 */

public interface ICartClearView<T> extends IBaseView {

    void onClearSucessful(T data);
    void onClearFailure(String message,int code);




}
