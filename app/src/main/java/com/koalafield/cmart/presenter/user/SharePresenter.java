package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.user.ShareBean;
import com.koalafield.cmart.ui.view.user.IShareView;

/**
 *
 * @author jiangrenming
 * @date 2018/6/20
 */

public class SharePresenter implements ISharePresenter{


    private IShareView mShareView;

    public SharePresenter(IShareView shareView){
        this.mShareView = shareView;
    }

    @Override
    public void getData() {
        ApiManager.getShareInfos().subscribe(new SubScribeCallBack<ShareBean>(new CallBack() {
            @Override
            public void onInit() {
                mShareView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    ShareBean shareBean = (ShareBean) data;
                    mShareView.onSucessFul(shareBean);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mShareView.hideLoading();
                mShareView.onFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mShareView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }
}
