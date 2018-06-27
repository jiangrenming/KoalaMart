package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public interface IShareCallView<T> extends IBaseView {

    void onShareSucessFul(T data);
    void onShareFailure(String message,int code);
}
