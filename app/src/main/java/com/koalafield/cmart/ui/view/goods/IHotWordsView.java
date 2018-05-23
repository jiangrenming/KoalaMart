package com.koalafield.cmart.ui.view.goods;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public interface IHotWordsView<T> extends IBaseView {

    void onHotWordsSucessFul(T data);
    void onHotWordsNoData();
    void onHotWordsFailure(String message);

}
