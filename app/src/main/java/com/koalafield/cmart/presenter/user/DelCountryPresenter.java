package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.user.IDelAddressView;
import com.koalafield.cmart.ui.view.user.IEditAddressView;

import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/24
 */

public class DelCountryPresenter implements  IDelCountryPresenter{

    private IDelAddressView delAddressView;

    public DelCountryPresenter(IDelAddressView delAddressView){
        this.delAddressView = delAddressView;
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void getParamsData(Map<String, String> params) {
        ApiManager.delAddresses(params).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                delAddressView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean bean = (BaseResponseBean) data;
                    if (bean != null && bean.getCode() == 200){
                        delAddressView.onDelAddressSucessFul(bean);
                    }else if (bean.getCode() == 401){
                        delAddressView.onDelAddressFailure(bean.getMsg(),bean.getCode());
                    }else {
                        delAddressView.onDelAddressFailure(bean.getMsg(),bean.getCode());
                    }
                }else {
                    delAddressView.onDelAddressFailure("返回的数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                delAddressView.hideLoading();
                delAddressView.onDelAddressFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                delAddressView.hideLoading();
            }
        }));
    }
}
