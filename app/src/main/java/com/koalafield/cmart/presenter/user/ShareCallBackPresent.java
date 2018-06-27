package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.user.IShareCallView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/6/27.
 */

public class ShareCallBackPresent implements  IShareCallBackPresent{

    private IShareCallView mShareView;
    private Map<String, String> params;

    public ShareCallBackPresent(IShareCallView mShareView){
        this.mShareView = mShareView;
    }


    @Override
    public void getData() {
        ApiManager.shareCallBack(params).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                mShareView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean datas = (BaseResponseBean) data;
                    mShareView.onShareSucessFul(datas);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mShareView.hideLoading();
                mShareView.onShareFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mShareView.hideLoading();
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
