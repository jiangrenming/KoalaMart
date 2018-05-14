package com.koalafield.cmart.presenter.categry;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.categry.CategryOneBean;
import com.koalafield.cmart.ui.view.categry.ICategryTwoView;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/14.
 */

public class CategryTwoPresenter implements ICategryTwoPresenter{


    private ICategryTwoView categryTwoView;

    public CategryTwoPresenter(ICategryTwoView mCate){
        this.categryTwoView = mCate;
    }


    @Override
    public void getData() {}

    @Override
    public void getMoreData() {}

    @Override
    public void getCategryTwoData(Map<String, String> params) {
        ApiManager.getCategryList(params).subscribe(new SubScribeCallBack<List<CategryOneBean>>(new CallBack() {
            @Override
            public void onInit() {
                categryTwoView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    List<CategryOneBean> categryOneBeen = (List<CategryOneBean>) data;
                    if (categryOneBeen != null &&categryOneBeen.size() >0){
                        categryTwoView.onSucessTwoFul(categryOneBeen);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                categryTwoView.hideLoading();
                categryTwoView.onFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                categryTwoView.hideLoading();
            }
        }));
    }
}
