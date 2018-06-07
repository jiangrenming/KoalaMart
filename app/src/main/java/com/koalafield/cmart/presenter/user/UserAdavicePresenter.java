package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.user.IUserAdaviceView;

import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/6/7
 */

public class UserAdavicePresenter implements IUserAdavicePresenter{

    private IUserAdaviceView mUserView;
    private Map<String, String> params;

    public UserAdavicePresenter(IUserAdaviceView userView){
        this.mUserView = userView;
    }

    @Override
    public void getData() {
        ApiManager.addAdavices(params).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                mUserView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean datas = (BaseResponseBean) data;
                    mUserView.onAdaviceSucessFul(datas);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mUserView.hideLoading();
                mUserView.onAdaviceFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mUserView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void setParams(Map<String, String> params) {
        this.params = params;
    }
}
