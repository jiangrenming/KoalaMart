package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.goods.GoodsCollectionsBean;
import com.koalafield.cmart.ui.view.user.ICollectionView;
import com.koalafield.cmart.ui.view.user.IPurchaseOffView;

import java.util.List;

/**
 * Created by jiangrenming on 2018/5/23.
 */

public class PurcashOffPresenter implements IPurcashOffPresenter {


    private IPurchaseOffView mPurchaseOffView;
    private  int pageIndex = 0;
    public PurcashOffPresenter(IPurchaseOffView purchaseOffView){
        this.mPurchaseOffView = purchaseOffView;
    }

    @Override
    public void getData() {
        pageIndex = 0;
        ApiManager.getGoodsCollection(pageIndex).subscribe(new SubScribeCallBack<List<GoodsCollectionsBean>>(new CallBack() {
            @Override
            public void onInit() {
                mPurchaseOffView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                List<GoodsCollectionsBean> collectionsBeen = (List<GoodsCollectionsBean>) data;
                if (collectionsBeen != null && collectionsBeen.size() > 0){
                    mPurchaseOffView.onPurchaseOffSucessFul(collectionsBeen);
                    pageIndex++;
                }else {
                    mPurchaseOffView.loadPurchaseOffEmptyData();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mPurchaseOffView.hideLoading();
                mPurchaseOffView.onPurchaseOffFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mPurchaseOffView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {
        ApiManager.getGoodsCollection(pageIndex).subscribe(new SubScribeCallBack<List<GoodsCollectionsBean>>(new CallBack() {
            @Override
            public void onInit() {
                mPurchaseOffView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                List<GoodsCollectionsBean> collectionsBeen = (List<GoodsCollectionsBean>) data;
                if (collectionsBeen != null && collectionsBeen.size() > 0){
                    mPurchaseOffView.loadPurchaseOffMoreData(collectionsBeen);
                    pageIndex++;
                }else {
                    mPurchaseOffView.loadPurchaseOffNoMoreData();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mPurchaseOffView.hideLoading();
                mPurchaseOffView.onPurchaseOffFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mPurchaseOffView.hideLoading();
            }
        }));
    }

}
