package com.koalafield.cmart.ui.view.order;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public interface ICreateOrderView<T> extends IBaseView {


    void onCreateOrderData(T data);
    void onCreateOrderFailure(String message);

}
