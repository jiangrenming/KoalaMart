package com.koalafield.cmart.ui.view.goods;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public interface IGoodsCommentView<T> extends IBaseView {

    void onGoodsCommentSucessFul(T data);
    void onGoodsCommentFailure(String message);
    void onLoadEmptyData();
    void onLoadNoMoreData();
    void onLoadMoreData(T data);
}
