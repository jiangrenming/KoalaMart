package com.koalafield.cmart.presenter.order;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.order.OrderPrice;
import com.koalafield.cmart.ui.view.order.IPriceView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/28.
 */

public class PricePresenter implements IPricePresenter{

    private IPriceView mPriceView;
    private Map<String, String> mParams;
    public PricePresenter(IPriceView priceView){
        this.mPriceView = priceView;
    }


    @Override
    public void getData() {
        ApiManager.getPrice(mParams).subscribe(new SubScribeCallBack<OrderPrice>(new CallBack() {
            @Override
            public void onInit() {
                mPriceView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                    if (data != null){
                        OrderPrice price = (OrderPrice) data;
                        if (price != null){
                            mPriceView.onPriceData(price);
                        }
                    }else {
                        mPriceView.onPriceFailure("返回的数据为NULL",0);
                    }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mPriceView.hideLoading();
                mPriceView.onPriceFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mPriceView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void setParams(Map<String, String> params) {
        mParams= params;
    }
}
