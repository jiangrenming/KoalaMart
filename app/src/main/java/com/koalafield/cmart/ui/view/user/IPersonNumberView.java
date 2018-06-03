package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public interface IPersonNumberView<T> extends IBaseView {

    void onPersonNumberSucessFul(T data);
    void onPersonNumberFailure(String message,int code);

}
