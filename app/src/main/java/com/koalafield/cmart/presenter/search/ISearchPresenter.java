package com.koalafield.cmart.presenter.search;

import com.koalafield.cmart.base.presenter.IBasePresenter;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public interface ISearchPresenter extends IBasePresenter {

    void setParams(Map<String,String>params);
}
