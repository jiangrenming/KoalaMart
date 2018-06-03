package com.koalafield.cmart.ui.view.categry;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/14.
 */

public interface ICategryView<T> extends IBaseView {

    void onSucessFul(T data);
    void onFailure(String message,int code);
}
