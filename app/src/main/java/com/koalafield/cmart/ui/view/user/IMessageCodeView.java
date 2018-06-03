package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public interface IMessageCodeView<T> extends IBaseView {

    void onMessageCodeFul(T data);
    void onMessageCodeFailure(String message);
}
