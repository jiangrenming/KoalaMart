package com.koalafield.cmart.presenter.user;

import com.koalafield.cmart.base.presenter.IBasePresenter;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/12.
 */

public interface IShareCallBackPresent extends IBasePresenter {

    /**
     * 设置参数
     * @param params
     */
    void setParams(Map<String, String> params);
}
