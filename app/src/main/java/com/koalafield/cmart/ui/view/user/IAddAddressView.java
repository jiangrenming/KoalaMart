package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public interface IAddAddressView<T> extends IBaseView {

    void onAddAddressSucessFul(T data);
    void onAddAddressFailure(String message,int code);

}
