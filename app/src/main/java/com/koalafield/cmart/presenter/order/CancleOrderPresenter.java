package com.koalafield.cmart.presenter.order;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.order.ICancleOrderView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/6/6.
 */

public class CancleOrderPresenter implements ICancleOrderPresenter{

    private ICancleOrderView orderView;
    private Map<String, String> params;
    public CancleOrderPresenter( ICancleOrderView orderView){
        this.orderView = orderView;
    }

    @Override
    public void getData() {
        ApiManager.cancle_order(params).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                orderView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean responseBean = (BaseResponseBean) data;
                    orderView.onSucessCancleOrder(responseBean);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                orderView.hideLoading();
                orderView.onFailureCancleOrder(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                orderView.hideLoading();
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
