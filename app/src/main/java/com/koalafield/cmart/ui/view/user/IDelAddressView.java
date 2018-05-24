package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public interface IDelAddressView<T> extends IBaseView {

    void onDelAddressSucessFul(T data);
    void onDelAddressFailure(String message);

}
