package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.user.RegisterBean;
import com.koalafield.cmart.ui.view.ILoginView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/13.
 */

public class LoginPrsenter implements ILoginPresenter{

    private  Map<String,String> mParams;
    private ILoginView mLoginView;

    public LoginPrsenter(ILoginView loginView){
        this.mLoginView = loginView;
    }


    @Override
    public void getData() {
        ApiManager.getLoginInfos(mParams).subscribe(new SubScribeCallBack<RegisterBean>(new CallBack() {
            @Override
            public void onInit() {
                mLoginView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    RegisterBean registerBean = (RegisterBean) data;
                    if (null != registerBean){
                        mLoginView.onSucessFul(registerBean);
                    }else {
                        mLoginView.onFailure("返回的数据null");
                    }
                }else {
                    mLoginView.onFailure("返回的数据有误");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mLoginView.hideLoading();
                mLoginView.onFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mLoginView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void setParams(Map<String, String> params) {
        this.mParams = params;
    }
}
