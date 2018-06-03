package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.user.IMessageCodeView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/6/1.
 */

public class MessageCodePresenter implements IMessageCodePresenter{

    private IMessageCodeView messageCodeView;
    private Map<String, String> mParams;
    public MessageCodePresenter(IMessageCodeView messageCodeView){
        this.messageCodeView = messageCodeView;
    }

    @Override
    public void getData() {
        ApiManager.getMessageCode(mParams).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                messageCodeView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean baseResponseBean = (BaseResponseBean) data;
                    if (baseResponseBean.getCode() == 200){
                        messageCodeView.onMessageCodeFul(baseResponseBean);
                    }else{
                        messageCodeView.onMessageCodeFailure(baseResponseBean.getMsg());
                    }
                }else {
                    messageCodeView.onMessageCodeFailure("返回的数据为NULL");
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                messageCodeView.hideLoading();
                messageCodeView.onMessageCodeFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                messageCodeView.hideLoading();
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
