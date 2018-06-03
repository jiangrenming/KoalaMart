package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.user.IAddAddressView;
import com.koalafield.cmart.ui.view.user.IEditAddressView;

import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/24
 */

public class EditCountryPresenter implements IEditCountryPresenter{


    private IEditAddressView editAddressView;

    public EditCountryPresenter(IEditAddressView editAddressView){
        this.editAddressView = editAddressView;
    }


    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void getParamsData(Map<String, String> params) {
        ApiManager.addAddresses(params).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                editAddressView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean bean = (BaseResponseBean) data;
                    if (bean != null && bean.getCode() == 200){
                        editAddressView.onEditAddressSucessFul(bean);
                    }else if (bean.getCode() == 401){
                        editAddressView.onEditAddressFailure(bean.getMsg(),bean.getCode());
                    }else {
                        editAddressView.onEditAddressFailure(bean.getMsg(),bean.getCode());
                    }
                }else {
                    editAddressView.onEditAddressFailure("返回的数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                editAddressView.hideLoading();
                editAddressView.onEditAddressFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                editAddressView.hideLoading();
            }
        }));
    }
}
