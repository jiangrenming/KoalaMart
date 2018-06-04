package com.koalafield.cmart.ui.view.order;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public interface IComfirmOrderView<T> extends IBaseView {


    void onComfirmOrderList(T data);
    void onFailureComfirmOrder(String message, int code);

}
