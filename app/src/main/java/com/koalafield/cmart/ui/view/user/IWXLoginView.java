package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public interface IWXLoginView<T> extends IBaseView {

    void onWXSucessFul(T data);
    void onWXFailure(String message);
}
