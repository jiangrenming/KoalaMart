package com.koalafield.cmart.ui.view;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public interface ILoginView<T> extends IBaseView {

    void onSucessFul(T data);
    void onFailure(String message);
}
