package com.koalafield.cmart.presenter.categry;

import com.koalafield.cmart.base.presenter.IBasePresenter;

import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/14
 */

public interface ICategryPresenter extends IBasePresenter{

    void  getCategryData(Map<String,String> params);
}
