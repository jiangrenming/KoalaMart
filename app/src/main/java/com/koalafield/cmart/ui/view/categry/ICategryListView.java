package com.koalafield.cmart.ui.view.categry;

import com.koalafield.cmart.base.view.IBaseView;

/**
 * Created by jiangrenming on 2018/5/14.
 */

public interface ICategryListView<T> extends IBaseView {

    void onCategryBrandSucessFul(T data);
    void onCategryBrandFailure(String message);
    void onEmptyData();
    void loadMoreData(T data);
    void loadNoMore();
}
