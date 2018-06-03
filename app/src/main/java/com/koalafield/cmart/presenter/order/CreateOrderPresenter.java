package com.koalafield.cmart.presenter.order;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.order.CreateOrderBean;
import com.koalafield.cmart.ui.view.order.ICreateOrderView;

import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/28
 */

public class CreateOrderPresenter implements  ICreateOrderPresenter{

    private ICreateOrderView createOrderView;
    private Map<String, String> mParams;
    public CreateOrderPresenter(ICreateOrderView createOrderView){
        this.createOrderView = createOrderView;
    }

    @Override
    public void getData() {
        ApiManager.createOrders(mParams).subscribe(new SubScribeCallBack<CreateOrderBean>(new CallBack() {
            @Override
            public void onInit() {
                createOrderView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    CreateOrderBean createOrderBean = (CreateOrderBean) data;
                    if (createOrderBean != null){
                        createOrderView.onCreateOrderData(createOrderBean);
                    }else {
                        createOrderView.onCreateOrderFailure("返回的数据有误",0);
                    }
                }else {
                    createOrderView.onCreateOrderFailure("返回的数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                createOrderView.hideLoading();
                 createOrderView.onCreateOrderFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                createOrderView.hideLoading();
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
