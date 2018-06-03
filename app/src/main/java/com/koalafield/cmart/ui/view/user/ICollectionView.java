package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public interface ICollectionView<T> extends IBaseView {

    void onCollectionSucessFul(T data);
    void onCollectionFailure(String message,int code);
    void loadCollectionEmptyData();
    void loadCollectionNoMoreData();
    void loadCollectionMoreData(T data);
}
