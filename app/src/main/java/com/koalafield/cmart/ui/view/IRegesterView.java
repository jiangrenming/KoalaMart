package com.koalafield.cmart.ui.view;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/12
 * 注册界面回调
 */

public interface IRegesterView<T> extends IBaseView {

    void onSucessFul(T data);
    void onFailure(String message);

}
