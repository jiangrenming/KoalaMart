package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.user.IUpdatePersonView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/6/1.
 */

public class UpdatePersonInfoPresenter implements IUpdatePersonInfoPresenter{

    private IUpdatePersonView mPersonView;
    private  Map<String, String> mParams;

    public UpdatePersonInfoPresenter(IUpdatePersonView personView){
        this.mPersonView = personView;
    }

    @Override
    public void getData() {
        ApiManager.upDatePerson(mParams).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                mPersonView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean responseBean = (BaseResponseBean) data;
                    if (responseBean != null && responseBean.getCode() == 200){
                        mPersonView.onUpDatePersonFul(responseBean);
                    }else {
                        mPersonView.onUpDatePersonFailure(responseBean.getMsg(),responseBean.getCode());
                    }
                }else {
                    mPersonView.onUpDatePersonFailure("返回的数据为NULl",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mPersonView.hideLoading();
                mPersonView.onUpDatePersonFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mPersonView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void setParams(Map<String, String> params) {
        mParams = params;
    }
}
