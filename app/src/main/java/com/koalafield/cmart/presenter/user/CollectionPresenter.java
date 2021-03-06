package com.koalafield.cmart.presenter.user;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.goods.GoodsCollectionsBean;
import com.koalafield.cmart.ui.view.user.ICollectionView;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/21
 */

public class CollectionPresenter implements  ICollectionPresenter{


    private ICollectionView mCollectionView;
    private  int pageIndex = 0;


    public CollectionPresenter(ICollectionView collectionView){
        this.mCollectionView = collectionView;
    }

    @Override
    public void getData() {
        pageIndex = 0;
        ApiManager.getGoodsCollection(pageIndex).subscribe(new SubScribeCallBack<List<GoodsCollectionsBean>>(new CallBack() {
            @Override
            public void onInit() {
                mCollectionView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                List<GoodsCollectionsBean> collectionsBeen = (List<GoodsCollectionsBean>) data;
                if (collectionsBeen != null && collectionsBeen.size() > 0){
                    mCollectionView.onCollectionSucessFul(collectionsBeen);
                    pageIndex++;
                }else {
                    mCollectionView.loadCollectionEmptyData();
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mCollectionView.hideLoading();
                mCollectionView.onCollectionFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mCollectionView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {
        ApiManager.getGoodsCollection(pageIndex).subscribe(new SubScribeCallBack<List<GoodsCollectionsBean>>(new CallBack() {
            @Override
            public void onInit() {
                mCollectionView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                List<GoodsCollectionsBean> collectionsBeen = (List<GoodsCollectionsBean>) data;
                if (collectionsBeen != null && collectionsBeen.size() > 0){
                    mCollectionView.loadCollectionMoreData(collectionsBeen);
                    pageIndex++;
                }else {
                    mCollectionView.loadCollectionNoMoreData();
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mCollectionView.hideLoading();
                mCollectionView.onCollectionFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mCollectionView.hideLoading();
            }
        }));
    }

}
