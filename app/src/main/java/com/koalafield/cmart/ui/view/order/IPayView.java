package com.koalafield.cmart.ui.view.order;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public interface IPayView<T> extends IBaseView {


    void onSubmitList(T data);
    void onSubmitFailure(String message);

}
