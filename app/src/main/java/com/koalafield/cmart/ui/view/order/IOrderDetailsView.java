package com.koalafield.cmart.ui.view.order;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public interface IOrderDetailsView<T> extends IBaseView {


    void onOrderDetails(T data);
    void onOrderDetailsFailure(String message,int code);

}
