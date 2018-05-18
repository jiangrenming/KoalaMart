package com.koalafield.cmart.presenter.goods;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.goods.GoodsDetailsBean;
import com.koalafield.cmart.ui.view.goods.IGoodsDetailView;
import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/18
 */

public class GoodsDetailPresenter implements IGoodsDetailPresenter{

    private IGoodsDetailView mGoodsDetailView;

    public GoodsDetailPresenter(IGoodsDetailView goodsDetailView){
        this.mGoodsDetailView = goodsDetailView;
    }
    @Override
    public void getDetails(Map<String, String> params) {
        ApiManager.getGoodsDetailsInfos(params).subscribe(new SubScribeCallBack<GoodsDetailsBean>(new CallBack() {
            @Override
            public void onInit() {
                mGoodsDetailView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    GoodsDetailsBean goodsDetailsBean = (GoodsDetailsBean) data;
                    if (goodsDetailsBean.getCode() == 0){
                        mGoodsDetailView.onGoodsDetailsSucessFul(goodsDetailsBean);
                    }else {
                        mGoodsDetailView.onGoodsDetailsFailure(goodsDetailsBean.getMsg());
                    }
                }else {
                    mGoodsDetailView.onGoodsDetailsFailure("返回的数据为NULL");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mGoodsDetailView.hideLoading();
                mGoodsDetailView.onGoodsDetailsFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mGoodsDetailView.hideLoading();
            }
        }));
    }
    @Override
    public void getData() {}

    @Override
    public void getMoreData() {}


}
