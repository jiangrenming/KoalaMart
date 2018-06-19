package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.user.AvtorBean;
import com.koalafield.cmart.ui.view.user.IAvtorView;

/**
 *
 * @author jiangrenming
 * @date 2018/6/19
 */

public class AvtorPresenter implements IAvtorPresenter{


    private String mPath;
    private IAvtorView mAvtor;
    public AvtorPresenter(IAvtorView mAvtor){
        this.mAvtor = mAvtor;
    }

    @Override
    public void getData() {
        ApiManager.changeAvtor(mPath).subscribe(new SubScribeCallBack<AvtorBean>(new CallBack() {
            @Override
            public void onInit() {
                mAvtor.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    AvtorBean avtorBean = (AvtorBean) data;
                    if (avtorBean != null){
                        mAvtor.onSucessFul(avtorBean);
                    }
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mAvtor.hideLoading();
                mAvtor.onFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mAvtor.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {}

    @Override
    public void setParams(String params) {
        this.mPath = params;
    }
}
