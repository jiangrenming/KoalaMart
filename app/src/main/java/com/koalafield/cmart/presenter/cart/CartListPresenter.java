package com.koalafield.cmart.presenter.cart;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.ui.view.cart.ICartListView;
import com.koalafield.cmart.widget.EmptyLayout;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/16
 */

public class CartListPresenter implements ICartListPresenter{



    private ICartListView cartListView;

    public CartListPresenter(ICartListView cartListView){
        this.cartListView = cartListView;
    }

    @Override
    public void getData() {
        ApiManager.getCategryList().subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                cartListView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data!= null){
                    BaseResponseBean responseBean = (BaseResponseBean) data;
                    if (responseBean.getCode() == 200){
                        List<CartDataBean> cartDataBeen  = (List<CartDataBean>) responseBean.getData();
                        if (cartDataBeen !=null && cartDataBeen.size() >0){
                            cartListView.onSucessCartFul(cartDataBeen);
                        }else {
                            cartListView.onLoadNoData();
                        }
                    }else if (responseBean.getCode() == 401){
                        cartListView.onFailureCart(String.valueOf(responseBean.getCode()),0);
                    }else {
                        cartListView.onFailureCart(responseBean.getMsg(),0);
                    }
                }else {
                    cartListView.onFailureCart("返回数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                cartListView.hideLoading();
                cartListView.onFailureCart(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                cartListView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }


    @Override
    public void loadNoData() {

    }
}
