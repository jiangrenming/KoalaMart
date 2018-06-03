package com.koalafield.cmart.presenter.cart;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.cart.CartDataBean;
import com.koalafield.cmart.bean.cart.CartIdBean;
import com.koalafield.cmart.bean.categry.CategryOneBean;
import com.koalafield.cmart.ui.view.cart.ICartChangeCountView;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/17
 */

public class CartChangeItemPresenter implements ICartChangeItemPresenter {


    private ICartChangeCountView mCartChangeCountView;
    public CartChangeItemPresenter(ICartChangeCountView cartChangeCountView){
        this.mCartChangeCountView = cartChangeCountView;
    }

    @Override
    public void getData() {
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void getChangeCountData(Map<String, String> params) {
        ApiManager.changeCartCount(params).subscribe(new SubScribeCallBack<CartIdBean>(new CallBack() {
            @Override
            public void onInit() {
                mCartChangeCountView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    CartIdBean responseBean = (CartIdBean) data;
                    if (responseBean != null ){
                        mCartChangeCountView.onChangeItemSucessful(responseBean);
                    }else {
                        mCartChangeCountView.onChangeItemFailure("添加失败",0);
                    }
                }else {
                    mCartChangeCountView.onChangeItemFailure("返回的数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                 mCartChangeCountView.hideLoading();
                 mCartChangeCountView.onChangeItemFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mCartChangeCountView.hideLoading();
            }
        }));
    }
}
