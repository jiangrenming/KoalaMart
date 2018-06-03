package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.user.IChangePwdView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/6/1.
 */

public class ChangePwdPresenter implements IChangePwdPresenter{



    private IChangePwdView mChangeView;

    private  Map<String, String> mParams;

    public ChangePwdPresenter(IChangePwdView changeView){
        this.mChangeView = changeView;
    }




    @Override
    public void getData() {
        ApiManager.changePwd(mParams).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                mChangeView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean responseBean = (BaseResponseBean) data;
                    if (responseBean != null && responseBean.getCode() == 200){
                        mChangeView.onChangePwdFul(responseBean);
                    }else {
                        mChangeView.onChangePwdFailure(responseBean.getMsg(),responseBean.getCode());

                    }
                }else {
                    mChangeView.onChangePwdFailure("返回数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mChangeView.hideLoading();
                mChangeView.onChangePwdFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mChangeView.hideLoading();
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
