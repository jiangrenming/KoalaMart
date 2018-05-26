package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.user.DisCountBean;
import com.koalafield.cmart.ui.view.user.IDisCountListView;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/25.
 */

public class DisCountPresenter implements  IDisCountPresenter{

    private IDisCountListView disCountListView;
    private Map<String,String> params ;


    public DisCountPresenter( IDisCountListView disCountListView){
            this.disCountListView = disCountListView;
    }


    @Override
    public void getData() {
        ApiManager.getDisCountList(params).subscribe(new SubScribeCallBack<List<DisCountBean>>(new CallBack() {
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
                }else {
                    disCountListView.onDisCountFailure("返回的数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                disCountListView.hideLoading();
                disCountListView.onDisCountFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                disCountListView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {
        ApiManager.getDisCountList(params).subscribe(new SubScribeCallBack<List<DisCountBean>>(new CallBack() {
            @Override
            public void onInit() {
                disCountListView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    List<DisCountBean> disCountBeen = (List<DisCountBean>) data;
                    if (disCountBeen != null && disCountBeen.size() >0){
                        disCountListView.loadDisCountMoreData(disCountBeen);
                    }else {
                        disCountListView.loadDisCountNoMoreData();
                    }
                }else {
                    disCountListView.onDisCountFailure("返回的数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                disCountListView.hideLoading();
                disCountListView.onDisCountFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                disCountListView.hideLoading();
            }
        }));
    }

    @Override
    public void setParams(Map<String,String> params) {
        this.params = params;
    }
}
