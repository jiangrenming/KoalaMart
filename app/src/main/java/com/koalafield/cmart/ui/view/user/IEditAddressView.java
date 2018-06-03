package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public interface IEditAddressView<T> extends IBaseView {

    void onEditAddressSucessFul(T data);
    void onEditAddressFailure(String message,int code);

}
