package com.koalafield.cmart.presenter.home;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.home.HomeBanaerBean;
import com.koalafield.cmart.bean.user.RegisterBean;
import com.koalafield.cmart.ui.view.home.IBananerView;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/14
 */

public class HomeBananerPresenter implements IHomeBananerPresenter{


    private IBananerView bananerView;

    public HomeBananerPresenter(IBananerView bananerView){
        this.bananerView = bananerView;
    }

    @Override
    public void getData() {
        ApiManager.getHomeBananerList().subscribe(new SubScribeCallBack<List<HomeBanaerBean>>(new CallBack() {
            @Override
            public void onInit() {
                bananerView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    List<HomeBanaerBean> bananers  = (List<HomeBanaerBean>) data;
                    if (null != bananers && bananers.size() > 0){
                        bananerView.onSucessFul(bananers);
                    }else {
                        bananerView.onFailure("返回的数据为空");
                    }
                }else {
                    bananerView.onFailure("数据为Null");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                bananerView.hideLoading();
                bananerView.onFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                bananerView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

    }
}
