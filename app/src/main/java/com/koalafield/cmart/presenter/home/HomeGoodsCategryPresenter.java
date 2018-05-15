package com.koalafield.cmart.presenter.home;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.home.GoodsCategryBean;
import com.koalafield.cmart.bean.home.HomeBanaerBean;
import com.koalafield.cmart.ui.view.home.IGoodsCategryView;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/15
 */

public class HomeGoodsCategryPresenter implements IHomeGoodsCategryPresenter{

    private IGoodsCategryView mGoodsCategryView;

    public  HomeGoodsCategryPresenter(IGoodsCategryView goodsCategryView){
        this.mGoodsCategryView = goodsCategryView;
    }


    @Override
    public void getData() {
        ApiManager.getHomeGoodsCategry().subscribe(new SubScribeCallBack<List<GoodsCategryBean>>(new CallBack() {
            @Override
            public void onInit() {
                mGoodsCategryView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (null != data){
                    List<GoodsCategryBean> goodsCategryBeen = (List<GoodsCategryBean>) data;
                    if (null != goodsCategryBeen && goodsCategryBeen.size() > 0){
                        mGoodsCategryView.onGoodsCategrySucessFul(goodsCategryBeen);
                    }else {
                        mGoodsCategryView.onGoodsCategrySucessFul("返回的数据为空");
                    }
                }else {
                    mGoodsCategryView.onGoodsCategrySucessFul("数据为Null");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mGoodsCategryView.hideLoading();
                mGoodsCategryView.onGoodsCategrySucessFul(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mGoodsCategryView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {
        //以后做加载更多

    }
}
