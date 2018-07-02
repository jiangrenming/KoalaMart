package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.user.DisCountBean;
import com.koalafield.cmart.ui.view.user.IDisCountListView;

import java.util.List;

/**
 * Created by jiangrenming on 2018/7/2.
 */

public class CurennyDisCountPresenter implements ICurennyDisCountPresenter{

    private IDisCountListView disCountListView;

    public CurennyDisCountPresenter( IDisCountListView disCountListView){
        this.disCountListView = disCountListView;
    }

    @Override
    public void getData() {
        ApiManager.getCurenyDisCountList().subscribe(new SubScribeCallBack<List<DisCountBean>>(new CallBack() {
            @Override
            public void onInit() {
                disCountListView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    List<DisCountBean> disCountBeen = (List<DisCountBean>) data;
                    if (disCountBeen != null && disCountBeen.size() >0){
                        disCountListView.onDisCountSucessFul(disCountBeen);
                    }else {
                        disCountListView.loadDisCountEmptyData();
                    }
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                disCountListView.hideLoading();
                disCountListView.onDisCountFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                disCountListView.hideLoading();
            }
        }));

    }

    @Override
    public void getMoreData() {

    }
}
