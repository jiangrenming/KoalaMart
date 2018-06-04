package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public interface IPersonInfosView<T> extends IBaseView {

    void onInfosSucessFul(T data);
    void onInfosFailure(String message,int code);
}
