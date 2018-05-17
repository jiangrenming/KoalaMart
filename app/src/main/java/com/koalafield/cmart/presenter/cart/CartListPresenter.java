package com.koalafield.cmart.presenter.cart;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.base.bean.SpecialResponseBean;
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
        ApiManager.getCategryList().subscribe(new SubScribeCallBack<SpecialResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                cartListView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data!= null){
                    SpecialResponseBean responseBean = (SpecialResponseBean) data;
                    if (responseBean.getCode() == 200){
                        List<CartDataBean> cartDataBeen  = (List<CartDataBean>) responseBean.getData();
                        if (cartDataBeen !=null && cartDataBeen.size() >0){
                            cartListView.onSucessCartFul(cartDataBeen);
                        }else {
                            cartListView.onLoadNoData();
                        }
                    }else if (responseBean.getCode() == 401){
                        cartListView.onFailureCart(String.valueOf(responseBean.getCode()));
                    }else {
                        cartListView.onFailureCart(responseBean.getMsg());
                    }
                }else {
                    cartListView.onFailureCart("返回数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                cartListView.hideLoading();
                cartListView.showNetError(new EmptyLayout.OnRetryListener() {
                    @Override
                    public void onRetry() {
                        getData();
                    }
                });
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
