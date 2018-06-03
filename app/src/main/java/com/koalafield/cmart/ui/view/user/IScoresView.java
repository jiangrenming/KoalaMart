package com.koalafield.cmart.ui.view.user;

import com.koalafield.cmart.base.view.IBaseView;

/**
 *
 * @author jiangrenming
 * @date 2018/5/13
 */

public interface IScoresView<T> extends IBaseView {

    void onScoresSucessFul(T data);
    void onScoresFailure(String message,int code);
    void loadScoresEmptyData();
    void loadScoresNoMoreData();
    void loadScoresMoreData(T data);
}
