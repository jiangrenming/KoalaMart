package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.user.RegisterBean;
import com.koalafield.cmart.ui.view.user.IWXLoginView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/6/5.
 */

public class WXLoginPresenter implements IWXLoginPresenter{

    private IWXLoginView wxLoginView;
    private  Map<String, String> params;
    public WXLoginPresenter(IWXLoginView wxLoginView){
        this.wxLoginView = wxLoginView;
    }

    @Override
    public void getData() {
        ApiManager.getWXLogin(params).subscribe(new SubScribeCallBack<RegisterBean>(new CallBack() {
            @Override
            public void onInit() {
                wxLoginView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    RegisterBean registerBean = (RegisterBean) data;
                    if (null != registerBean){
                        wxLoginView.onWXSucessFul(registerBean);
                    }else {
                        wxLoginView.onWXFailure("返回的数据null");
                    }
                }else {
                    wxLoginView.onWXFailure("返回的数据有误");
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                wxLoginView.hideLoading();
                wxLoginView.onWXFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                wxLoginView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
