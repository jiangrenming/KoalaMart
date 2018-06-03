package com.koalafield.cmart.presenter.goods;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.BaseResponseBean;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.ui.view.goods.IGoodsCollectionView;

import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/19.
 */

public class GoodsCollectionPresenter implements IGoodsCollectionPresenter{


    private IGoodsCollectionView collectionView;


    public GoodsCollectionPresenter(IGoodsCollectionView collectionView){
        this.collectionView = collectionView;

    }
    @Override
    public void getData() {

    }

    @Override
    public void getMoreData() {

    }

    @Override
    public void getDetails(Map<String, String> params) {
        ApiManager.goods_collections(params).subscribe(new SubScribeCallBack<BaseResponseBean>(new CallBack() {
            @Override
            public void onInit() {
                collectionView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    BaseResponseBean responseBean = (BaseResponseBean) data;
                    if (responseBean.getCode() == 200){
                        collectionView.onGoodsCollectionsSucessFul(responseBean);
                    }else {
                        collectionView.onGoodsCollectionsFailure(responseBean.getMsg(),0);
                    }
                }else {
                    collectionView.onGoodsCollectionsFailure("返回的数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                collectionView.hideLoading();
                collectionView.onGoodsCollectionsFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                collectionView.hideLoading();

            }
        }));
    }
}
