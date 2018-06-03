package com.koalafield.cmart.ui.view.order;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public interface IOrderView<T> extends IBaseView {


    void onSucessOrderList(T data);
    void onFailureOrder(String message,int code);
    void loadEmptyData();
    void loadMoreData(T data);
    void loadNoMoreData();

}
