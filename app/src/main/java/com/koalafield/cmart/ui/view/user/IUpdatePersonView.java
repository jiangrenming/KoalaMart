package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public interface IUpdatePersonView<T> extends IBaseView {

    void onUpDatePersonFul(T data);
    void onUpDatePersonFailure(String message,int code);
}
