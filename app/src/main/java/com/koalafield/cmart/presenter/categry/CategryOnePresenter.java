package com.koalafield.cmart.presenter.categry;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.categry.CategryOneBean;
import com.koalafield.cmart.bean.categry.CategryTwoBean;
import com.koalafield.cmart.ui.view.categry.ICategryView;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/14
 * 分类列表
 */

public class CategryOnePresenter implements ICategryPresenter{

    private ICategryView mCategryView;

    public CategryOnePresenter(ICategryView mCategryView){
        this.mCategryView = mCategryView;
    }


    @Override
    public void getData() {}

    @Override
    public void getMoreData() {}


    @Override
    public void getCategryData(Map<String, String> params) {
        ApiManager.getCategryList(params).subscribe(new SubScribeCallBack<List<CategryOneBean>>(new CallBack() {
            @Override
            public void onInit() {
                mCategryView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    List<CategryOneBean> categryOneBeen = (List<CategryOneBean>) data;
                    if (categryOneBeen != null &&categryOneBeen.size() >0){
                        mCategryView.onSucessFul(categryOneBeen);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mCategryView.hideLoading();
                mCategryView.onFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mCategryView.hideLoading();
            }
        }));
    }

    /**
     * 二级列表数据
     * @param params
     */
    @Override
    public void getCategryTwoData(Map<String, String> params) {
        ApiManager.getCategryTwo(params).subscribe(new SubScribeCallBack<List<CategryTwoBean>>(new CallBack() {
            @Override
            public void onInit() {
                mCategryView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    List<CategryTwoBean> CategryTwoBean = (List<CategryTwoBean>) data;
                    if (CategryTwoBean != null &&CategryTwoBean.size() >0){
                        mCategryView.onSucessTwo(CategryTwoBean);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mCategryView.hideLoading();
                mCategryView.onFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mCategryView.hideLoading();
            }
        }));
    }
}
