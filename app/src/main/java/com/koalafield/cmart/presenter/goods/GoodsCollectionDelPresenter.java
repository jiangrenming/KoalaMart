package com.koalafield.cmart.presenter.goods;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.goods.IGoodsCollectionDeleteView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/19.
 */

public class GoodsCollectionDelPresenter implements IGoodsCollectionDelPresenter{

    private IGoodsCollectionDeleteView deleteView;

    public GoodsCollectionDelPresenter(IGoodsCollectionDeleteView deleteView){
        this.deleteView =deleteView;
    }

    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void getDetails(Map<String, String> params) {
        ApiManager.goods_delete_collections(params).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                deleteView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean responseBean = (BaseResponseBean) data;
                    if (responseBean.getCode() == 200){
                        deleteView.onGoodsCollectionDelSucessFul(responseBean);
                    }else {
                        deleteView.onGoodsCollectionDelFailure(responseBean.getMsg(),0);
                    }
                }else {
                    deleteView.onGoodsCollectionDelFailure("返回的数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                deleteView.hideLoading();
                deleteView.onGoodsCollectionDelFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                deleteView.hideLoading();

            }
        }));
    }
}
