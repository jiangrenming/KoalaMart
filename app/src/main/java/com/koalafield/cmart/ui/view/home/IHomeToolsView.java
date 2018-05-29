package com.koalafield.cmart.ui.view.home;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/14.
 */

public interface IHomeToolsView<T> extends IBaseView {

    void onToolsBarSucessFul(T data);
    void onToolsBarFailure(String message);

}
