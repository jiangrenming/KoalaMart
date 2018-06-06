package com.koalafield.cmart.ui.view.order;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public interface ICancleOrderView<T> extends IBaseView {


    void onSucessCancleOrder(T data);
    void onFailureCancleOrder(String message, int code);

}
