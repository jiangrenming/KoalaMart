package com.koalafield.cmart.presenter.order;

import com.koalafield.cmart.base.presenter.IBasePresenter;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public interface ICancleOrderPresenter extends IBasePresenter {

    void setParams(Map<String, String> params);

}
