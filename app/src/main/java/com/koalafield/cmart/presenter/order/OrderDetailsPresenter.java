package com.koalafield.cmart.presenter.order;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.order.OrderdetailsBean;
import com.koalafield.cmart.ui.view.order.IOrderDetailsView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/29.
 */

public class OrderDetailsPresenter implements  IOrderDetailsPresenter{

    private IOrderDetailsView detailsView;
    private  Map<String, String> mParams;

    public OrderDetailsPresenter(IOrderDetailsView detailsView){
        this.detailsView = detailsView;
    }

    @Override
    public void getData() {
        ApiManager.getOrderDetails(mParams).subscribe(new SubScribeCallBack<OrderdetailsBean>(new CallBack() {
            @Override
            public void onInit() {
                detailsView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    OrderdetailsBean orderdetailsBean = (OrderdetailsBean) data;
                    if (orderdetailsBean != null){
                        detailsView.onOrderDetails(orderdetailsBean);
                    }else {
                        detailsView.onOrderDetailsFailure("返回数据有误");
                    }
                }else {
                    detailsView.onOrderDetailsFailure("返回数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                detailsView.hideLoading();
                detailsView.onOrderDetailsFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                detailsView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void setParams(Map<String, String> params) {
        mParams = params;
    }
}
