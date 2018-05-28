package com.koalafield.cmart.presenter.order;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.order.SdkPayBean;
import com.koalafield.cmart.ui.view.order.IPaySdkView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/28.
 */

public class PaySdkPresenter implements  IPaySdkPresenter{

    private IPaySdkView sdkView;
    private  Map<String, String> params;
    public PaySdkPresenter(IPaySdkView sdkView){
        this.sdkView = sdkView;
    }


    @Override
    public void getData() {
        ApiManager.createSdkPay(params).subscribe(new SubScribeCallBack<SdkPayBean>(new CallBack() {
            @Override
            public void onInit() {
                sdkView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    SdkPayBean sdkPayBean = (SdkPayBean) data;
                    if (sdkPayBean != null){
                        sdkView.onPaySdkData(sdkPayBean);
                    }else {
                        sdkView.onPaySdkFailure("返回数据有误");
                    }
                }else {
                    sdkView.onPaySdkFailure("返回数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                sdkView.hideLoading();
                sdkView.onPaySdkFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                sdkView.hideLoading();
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
