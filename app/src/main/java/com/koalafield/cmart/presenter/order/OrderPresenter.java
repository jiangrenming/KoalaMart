package com.koalafield.cmart.presenter.order;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.order.OrderListBean;
import com.koalafield.cmart.ui.view.order.IOrderView;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/26.
 */

public class OrderPresenter implements IOrderPresenter{


    public IOrderView mOrderView;
    private Map<String,String> mParams;
    private  int pageIndex = 0;


    public OrderPresenter(IOrderView orderView){
        this.mOrderView = orderView;
    }

    @Override
    public void getData() {
        pageIndex =0;
        mParams.put("pageIndex",String.valueOf(pageIndex));
        ApiManager.getOrderList(mParams).subscribe(new SubScribeCallBack<List<OrderListBean>>(new CallBack() {
            @Override
            public void onInit() {
                mOrderView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    List<OrderListBean> orderListBeen = (List<OrderListBean>) data;
                    if (orderListBeen != null && orderListBeen.size() >0){
                        mOrderView.onSucessOrderList(orderListBeen);
                        pageIndex++;
                    }else {
                        mOrderView.loadEmptyData();
                    }
                }else {
                    mOrderView.onFailureOrder("返回数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mOrderView.hideLoading();
                mOrderView.onFailureOrder(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mOrderView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {
        mParams.put("pageIndex",String.valueOf(pageIndex));
        ApiManager.getOrderList(mParams).subscribe(new SubScribeCallBack<List<OrderListBean>>(new CallBack() {
            @Override
            public void onInit() {
                mOrderView.showLoading();

            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    List<OrderListBean> orderListBeen = (List<OrderListBean>) data;
                    if (orderListBeen != null && orderListBeen.size() >0){
                        mOrderView.loadMoreData(orderListBeen);
                        pageIndex++;
                    }else {
                        mOrderView.loadNoMoreData();
                    }
                }else {
                    mOrderView.onFailureOrder("返回数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mOrderView.hideLoading();
                mOrderView.onFailureOrder(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mOrderView.hideLoading();

            }
        }));

    }


    @Override
    public void setType(Map<String, String> params) {
        this.mParams = params;
    }
}
