package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public interface IPurchaseOffView<T> extends IBaseView {

    void onPurchaseOffSucessFul(T data);
    void onPurchaseOffFailure(String message);
    void loadPurchaseOffEmptyData();
    void loadPurchaseOffNoMoreData();
    void loadPurchaseOffMoreData(T data);
}
