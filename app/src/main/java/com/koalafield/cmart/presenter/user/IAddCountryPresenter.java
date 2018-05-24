package com.koalafield.cmart.presenter.user;

import com.koalafield.cmart.base.presenter.IBasePresenter;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public interface IAddCountryPresenter extends IBasePresenter {

    /**
     * 获取网络数据
     */
    void getParamsData(Map<String,String> params);

}
