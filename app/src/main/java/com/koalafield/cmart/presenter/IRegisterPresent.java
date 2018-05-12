package com.koalafield.cmart.presenter;

import com.koalafield.cmart.base.presenter.IBasePresenter;
import com.koalafield.cmart.ui.view.IRegesterView;

import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/12
 * 注册的p层接口实现
 */

public class IRegisterPresent implements IBasePresenter {

    private IRegesterView mRegesterView;
    private  Map<String,String> mParams;

    public IRegisterPresent(IRegesterView regesterView, Map<String,String> params){
        this.mRegesterView = regesterView;
        this.mParams = params;
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }
}
