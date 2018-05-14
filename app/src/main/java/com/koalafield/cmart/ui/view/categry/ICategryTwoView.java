package com.koalafield.cmart.ui.view.categry;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/14.
 */

public interface ICategryTwoView<T> extends IBaseView{

    void onSucessTwoFul(T data);
    void onFailure(String message);
}
