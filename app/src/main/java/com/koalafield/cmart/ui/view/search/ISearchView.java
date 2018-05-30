package com.koalafield.cmart.ui.view.search;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/14.
 */

public interface ISearchView<T> extends IBaseView {

    void onSearchSucessFul(T data);
    void onSearchFailure(String message);
    void onEmptyData();
    void loadMoreData(T data);
    void loadNoMoreData();

}
