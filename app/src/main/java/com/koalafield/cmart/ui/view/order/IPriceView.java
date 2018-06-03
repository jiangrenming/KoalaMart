package com.koalafield.cmart.ui.view.order;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public interface IPriceView<T> extends IBaseView {


    void onPriceData(T data);
    void onPriceFailure(String message,int code);

}
