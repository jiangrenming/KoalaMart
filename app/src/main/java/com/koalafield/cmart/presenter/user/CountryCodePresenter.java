package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.user.CountryCode;
import com.koalafield.cmart.ui.view.user.ICountryCodeView;

import java.util.List;

/**
 * Created by jiangrenming on 2018/6/1.
 */

public class CountryCodePresenter implements ICountryCodePresenter{

    private ICountryCodeView mCountryCode;

    public CountryCodePresenter(ICountryCodeView countryCode){
        this.mCountryCode = countryCode;
    }


    @Override
    public void getData() {
        ApiManager.getCountryCode().subscribe(new SubScribeCallBack<List<CountryCode>>(new CallBack() {
            @Override
            public void onInit() {
                mCountryCode.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    List<CountryCode> countryCodes = (List<CountryCode>) data;
                    if (countryCodes != null && countryCodes.size() >0){
                        mCountryCode.onCountryCodeFul(countryCodes);
                    }else {
                        mCountryCode.onCountryCodeFailure("返回数据为空");
                    }
                }else {
                    mCountryCode.onCountryCodeFailure("返回数据为NULL");
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mCountryCode.hideLoading();
                mCountryCode.onCountryCodeFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mCountryCode.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }
}
