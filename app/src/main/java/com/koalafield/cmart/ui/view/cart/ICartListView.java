package com.koalafield.cmart.ui.view.cart;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/16.
 */

public interface ICartListView<T> extends IBaseView {

    void onSucessCartFul(T data);
    void onFailureCart(String message);
    void  onLoadNoData();

}
