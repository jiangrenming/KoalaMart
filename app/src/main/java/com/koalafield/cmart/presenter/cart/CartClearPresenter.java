package com.koalafield.cmart.presenter.cart;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.ui.view.cart.ICartClearView;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/17
 */

public class CartClearPresenter implements ICartClearPresenter {

    private ICartClearView mCartClearView;

    public CartClearPresenter(ICartClearView cartClearView){
        this.mCartClearView = cartClearView;
    }


    @Override
    public void getData() {
        ApiManager.clearCartGoods().subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                mCartClearView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data!= null){
                    BaseResponseBean clear = (BaseResponseBean) data;
                    if (clear.getCode() == 200){
                        mCartClearView.onClearSucessful(clear);
                    }else {
                        mCartClearView.onClearFailure(clear.getMsg(),0);
                    }
                }else {
                    mCartClearView.onClearFailure("返回数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mCartClearView.hideLoading();
                mCartClearView.onClearFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mCartClearView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }
}
