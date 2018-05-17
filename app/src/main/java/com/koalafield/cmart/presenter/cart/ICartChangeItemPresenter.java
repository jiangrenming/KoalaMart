package com.koalafield.cmart.presenter.cart;

import com.koalafield.cmart.base.presenter.IBasePresenter;

import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/15
 */

public interface ICartChangeItemPresenter extends IBasePresenter {

    void getChangeCountData(Map<String,String> params);
}
