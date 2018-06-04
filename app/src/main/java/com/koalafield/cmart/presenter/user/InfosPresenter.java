package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.user.PersonInfos;
import com.koalafield.cmart.ui.view.user.IPersonInfosView;

/**
 * Created by jiangrenming on 2018/6/4.
 */

public class InfosPresenter implements IInfosPresenter{


    private IPersonInfosView mPersonView;
    public InfosPresenter(IPersonInfosView personView){
        this.mPersonView = personView;
    }
    @Override
    public void getData() {
        ApiManager.getPersonInfos().subscribe(new SubScribeCallBack<PersonInfos>(new CallBack() {
            @Override
            public void onInit() {
                mPersonView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    PersonInfos infos = (PersonInfos) data;
                    if (infos != null){
                        mPersonView.onInfosSucessFul(infos);
                    }
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mPersonView.hideLoading();
                mPersonView.onInfosFailure(t.getMessage(),t.getCode());
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
}
