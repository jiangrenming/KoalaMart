package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public interface IDisCountListView<T> extends IBaseView {

    void onDisCountSucessFul(T data);
    void onDisCountFailure(String message,int code);
    void loadDisCountEmptyData();
    void loadDisCountNoMoreData();
    void loadDisCountMoreData(T data);
}
