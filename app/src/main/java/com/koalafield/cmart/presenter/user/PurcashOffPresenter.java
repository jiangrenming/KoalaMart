package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.goods.GoodsCollectionsBean;
import com.koalafield.cmart.bean.user.PurchaseOffBean;
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
        ApiManager.getGoodsPurchaseOff(pageIndex).subscribe(new SubScribeCallBack<List<PurchaseOffBean>>(new CallBack() {
            @Override
            public void onInit() {
                mPurchaseOffView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                List<PurchaseOffBean> collectionsBeen = (List<PurchaseOffBean>) data;
                if (collectionsBeen != null && collectionsBeen.size() > 0){
                    mPurchaseOffView.onPurchaseOffSucessFul(collectionsBeen);
                    pageIndex++;
                }else {
                    mPurchaseOffView.loadPurchaseOffEmptyData();
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mPurchaseOffView.hideLoading();
                mPurchaseOffView.onPurchaseOffFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mPurchaseOffView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {
        ApiManager.getGoodsPurchaseOff(pageIndex).subscribe(new SubScribeCallBack<List<PurchaseOffBean>>(new CallBack() {
            @Override
            public void onInit() {
                mPurchaseOffView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                List<PurchaseOffBean> collectionsBeen = (List<PurchaseOffBean>) data;
                if (collectionsBeen != null && collectionsBeen.size() > 0){
                    mPurchaseOffView.loadPurchaseOffMoreData(collectionsBeen);
                    pageIndex++;
                }else {
                    mPurchaseOffView.loadPurchaseOffNoMoreData();
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mPurchaseOffView.hideLoading();
                mPurchaseOffView.onPurchaseOffFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mPurchaseOffView.hideLoading();
            }
        }));
    }

}
