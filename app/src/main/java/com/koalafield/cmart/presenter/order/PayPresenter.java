package com.koalafield.cmart.presenter.order;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.order.PayBean;
import com.koalafield.cmart.ui.view.order.IPayView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/27.
 */

public class PayPresenter implements IPayPresenter{


    private IPayView mPayView;

    public PayPresenter(IPayView payView){
        this.mPayView = payView;
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void setParams(final Map<String, String> params) {
        ApiManager.getPays(params).subscribe(new SubScribeCallBack<PayBean>(new CallBack() {
            @Override
            public void onInit() {
                mPayView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data !=null){
                    PayBean payBean = (PayBean) data;
                    if (payBean!= null){
                        mPayView.onSubmitList(payBean);
                    }else {
                        mPayView.onSubmitFailure("返回的数据null",0);
                    }
                }else {
                    mPayView.onSubmitFailure("返回的数据有误",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mPayView.hideLoading();
                mPayView.onSubmitFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mPayView.hideLoading();
            }
        }));
    }
}
