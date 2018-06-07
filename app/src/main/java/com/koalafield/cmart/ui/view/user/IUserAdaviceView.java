package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public interface IUserAdaviceView<T> extends IBaseView {

    void onAdaviceSucessFul(T data);
    void onAdaviceFailure(String message,int code);
}
