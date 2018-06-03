package com.koalafield.cmart.presenter.cart;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.cart.CartNumberBean;
import com.koalafield.cmart.bean.categry.CategryOneBean;
import com.koalafield.cmart.ui.view.cart.ICartVIew;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/15.
 */

public class CartPresenter implements ICartPresenter{


    private ICartVIew cartVIew;
    public CartPresenter(ICartVIew cartVIew){
        this.cartVIew = cartVIew;
    }

    @Override
    public void getData() {
        ApiManager.getCartNums().subscribe(new SubScribeCallBack<CartNumberBean>(new CallBack() {
            @Override
            public void onInit() {
                cartVIew.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    CartNumberBean cartNumberBean = (CartNumberBean) data;
                    if (null != cartNumberBean){
                        cartVIew.onSucessNumberful(cartNumberBean);
                    }
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                cartVIew.hideLoading();
                cartVIew.onNumberFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                cartVIew.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }
}
