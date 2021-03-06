package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.user.IAddAddressView;

import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/24
 * 添加地址
 */

public class AddCountryPresenter implements  IAddCountryPresenter{


    private IAddAddressView addAddressView;

    public AddCountryPresenter(IAddAddressView addAddressView){
        this.addAddressView = addAddressView;
    }

    @Override
    public void getData() {}

    @Override
    public void getMoreData() {}

    @Override
    public void getParamsData(Map<String, String> params) {
        ApiManager.addAddresses(params).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                addAddressView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean bean = (BaseResponseBean) data;
                    if (bean != null && bean.getCode() == 200){
                        addAddressView.onAddAddressSucessFul(bean);
                    }else if (bean.getCode() == 401){
                        addAddressView.onAddAddressFailure(bean.getMsg(),bean.getCode());
                    }else {
                        addAddressView.onAddAddressFailure(bean.getMsg(),0);
                    }
                }else {
                    addAddressView.onAddAddressFailure("返回的数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                addAddressView.hideLoading();
                addAddressView.onAddAddressFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                addAddressView.hideLoading();
            }
        }));
    }
}
