package com.koalafield.cmart.presenter.goods;

import com.koalafield.cmart.base.presenter.IBasePresenter;

import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public interface IGoodsCommentPresenter extends IBasePresenter {

    void getComments(Map<String,String> params);
    void getMoreComments(Map<String,String> params);
}
