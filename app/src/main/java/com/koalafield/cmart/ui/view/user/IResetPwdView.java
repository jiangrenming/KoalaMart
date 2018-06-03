package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public interface IResetPwdView<T> extends IBaseView {

    void onResetPwdFul(T data);
    void onResetPwdFailure(String message,int code);
}
