package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public interface IAddressDetailView<T> extends IBaseView {

    void onAddressDetailSucessFul(T data);
    void onAddressDetailFailure(String message,int code);

}
