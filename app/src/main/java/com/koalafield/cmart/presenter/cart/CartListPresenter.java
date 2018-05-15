package com.koalafield.cmart.presenter.cart;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.ui.view.cart.ICartListView;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/16.
 */

public class CartListPresenter implements ICartListPresenter{



    private ICartListView cartListView;

    public CartListPresenter(ICartListView cartListView){
        this.cartListView = cartListView;
    }

    @Override
    public void getData() {
        ApiManager.getCategryList().subscribe(new SubScribeCallBack<List<CartDataBean>>(new CallBack() {
            @Override
            public void onInit() {
                cartListView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data!= null){
                    List<CartDataBean> cartDataBeen = (List<CartDataBean>) data;
                    if (cartDataBeen !=null && cartDataBeen.size() >0){
                        cartListView.onSucessCartFul(cartDataBeen);
                    }else {
                        cartListView.onLoadNoData();
                    }
                }else {
                    cartListView.onFailureCart("返回数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                cartListView.hideLoading();
                cartListView.onFailureCart(t.getMessage());
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
