package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public interface IChangePwdView<T> extends IBaseView {

    void onChangePwdFul(T data);
    void onChangePwdFailure(String message,int code);
}
