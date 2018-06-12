package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public interface IAddressListView<T> extends IBaseView {

    void onAddressSucessFul(T data);
    void onAddressFailure(String message,int code);
    void loadAddressEmptyData();
}
