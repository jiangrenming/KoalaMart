package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.user.IResetPwdView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/6/1.
 */

public class ResetPwdPresenter implements IResetPwdPresenter{

    private Map<String, String> mParams;

    private IResetPwdView mResetPwd;
    public ResetPwdPresenter(IResetPwdView mResetPwd){
        this.mResetPwd = mResetPwd;
    }

    @Override
    public void getData() {
        ApiManager.resetPwd(mParams).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                mResetPwd.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean responseBean = (BaseResponseBean) data;
                    if (responseBean != null && responseBean.getCode() == 200){
                        mResetPwd.onResetPwdFul(responseBean);
                    }else {
                        mResetPwd.onResetPwdFailure(responseBean.getMsg(),responseBean.getCode());
                    }
                }else {
                    mResetPwd.onResetPwdFailure("返回数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mResetPwd.hideLoading();
                mResetPwd.onResetPwdFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mResetPwd.hideLoading();
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
