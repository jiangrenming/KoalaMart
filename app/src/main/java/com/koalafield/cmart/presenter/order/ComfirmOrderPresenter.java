package com.koalafield.cmart.presenter.order;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.order.IComfirmOrderView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/6/4.
 */

public class ComfirmOrderPresenter implements IComfirmOrderPresenter{

    private IComfirmOrderView mComfirmOrder;
    private  Map<String, String> mParams;

    public ComfirmOrderPresenter(IComfirmOrderView comfirmOrder){
        this.mComfirmOrder =comfirmOrder;
    }

    @Override
    public void getData() {
        ApiManager.getComfirmOrder(mParams).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                mComfirmOrder.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean responseBean = (BaseResponseBean) data;
                    mComfirmOrder.onComfirmOrderList(responseBean);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mComfirmOrder.hideLoading();
                mComfirmOrder.onFailureComfirmOrder(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mComfirmOrder.hideLoading();
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
