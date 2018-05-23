package com.koalafield.cmart.presenter.cart;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.base.bean.BaseResponseBean;
import com.koalafield.cmart.base.bean.SpecialResponseBean;
import com.koalafield.cmart.bean.cart.CartDataBean;
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
        ApiManager.changeCartCount(params).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                mCartChangeCountView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    BaseResponseBean responseBean = (BaseResponseBean) data;
                    if (responseBean != null && responseBean.getCode() ==200){
                        mCartChangeCountView.onChangeItemSucessful(responseBean);
                    }else if ( responseBean.getCode() == 401){
                        mCartChangeCountView.onChangeItemFailure(String.valueOf(responseBean.getCode()));
                    }else {
                        mCartChangeCountView.onChangeItemFailure(responseBean.getMsg());
                    }
                }else {
                    mCartChangeCountView.onChangeItemFailure("返回的数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mCartChangeCountView.hideLoading();
                 mCartChangeCountView.onChangeItemFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mCartChangeCountView.hideLoading();
            }
        }));
    }
}
