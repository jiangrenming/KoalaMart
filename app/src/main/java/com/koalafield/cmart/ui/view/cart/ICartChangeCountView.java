package com.koalafield.cmart.ui.view.cart;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/15
 */

public interface ICartChangeCountView<T> extends IBaseView {

    void onChangeItemSucessful(T data);
    void onChangeItemFailure(String message,int code);




}
