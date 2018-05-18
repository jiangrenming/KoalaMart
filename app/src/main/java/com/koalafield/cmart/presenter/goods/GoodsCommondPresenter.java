package com.koalafield.cmart.presenter.goods;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.goods.GoodsDetailsBean;
import com.koalafield.cmart.bean.goods.GoodsRecoomendBean;
import com.koalafield.cmart.ui.view.goods.IGoodsCommondlView;

import java.util.List;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public class GoodsCommondPresenter implements IGoodsCommondPresenter{

    private IGoodsCommondlView mGoodsCommondView;

    public GoodsCommondPresenter(IGoodsCommondlView goodsCommondView){
        this.mGoodsCommondView = goodsCommondView;
    }

    @Override
    public void getCommondGoods(int goodsId) {
        ApiManager.getGoodsRecommends(goodsId).subscribe(new SubScribeCallBack<List<GoodsRecoomendBean>>(new CallBack() {
            @Override
            public void onInit() {
                mGoodsCommondView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    List<GoodsRecoomendBean> goodsRecoomendBean = (List<GoodsRecoomendBean>) data;
                    if (goodsRecoomendBean != null && goodsRecoomendBean.size() >0){
                        mGoodsCommondView.onGoodsCommondSucessFul(goodsRecoomendBean);
                    }else {
                        mGoodsCommondView.onGoodsCommondFailure("返回的数据为空");
                    }
                }else {
                    mGoodsCommondView.onGoodsCommondFailure("返回的数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mGoodsCommondView.hideLoading();
                mGoodsCommondView.onGoodsCommondFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mGoodsCommondView.hideLoading();
            }
        }));
    }

    @Override
    public void getData() {}

    @Override
    public void getMoreData() {}


}
