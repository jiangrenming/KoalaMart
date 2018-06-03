package com.koalafield.cmart.presenter.home;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.home.ToolsBarBean;
import com.koalafield.cmart.ui.view.home.IHomeToolsView;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/30.
 */

public class HomeToolsBarPresenter implements IHomeToolsBarPresenter{

    private IHomeToolsView toolsView;
    public HomeToolsBarPresenter(IHomeToolsView toolsView){
        this.toolsView = toolsView;
    }

    @Override
    public void getData() {
        ApiManager.getHomeToolsBar().subscribe(new SubScribeCallBack<List<ToolsBarBean>>(new CallBack() {
            @Override
            public void onInit() {
                toolsView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    List<ToolsBarBean> toolsBarBeen = (List<ToolsBarBean>) data;
                    if (toolsBarBeen != null && toolsBarBeen.size() >0){
                        toolsView.onToolsBarSucessFul(toolsBarBeen);
                    }else {
                        toolsView.onToolsBarFailure("返回的数据有误");
                    }
                }else {
                    toolsView.onToolsBarFailure("返回的数据为null");
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                toolsView.hideLoading();
                toolsView.onToolsBarFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                toolsView.hideLoading();

            }
        }));
    }

    @Override
    public void getMoreData() {

    }
}
