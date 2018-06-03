package com.koalafield.cmart.presenter.categry;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.jrm.retrofitlibrary.retrofit.ExceptionHandle;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.categry.CateBrandGoodsListBean;
import com.koalafield.cmart.bean.categry.GoodsListBean;
import com.koalafield.cmart.ui.view.categry.ICategryListView;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jiangrenming
 * @date 2018/5/31
 */

public class CategryBrandPresenter implements ICategryBrandPresenter{


    private ICategryListView mListView;
    private  Map<String, String> mParams;
    private  int pageIndex = 0;

    public CategryBrandPresenter(ICategryListView mListView){
        this.mListView = mListView;
    }

    @Override
    public void getData() {
        mParams.put("pageIndex",String.valueOf(0));
        ApiManager.getCategryLists(mParams).subscribe(new SubScribeCallBack<CateBrandGoodsListBean>(new CallBack() {
            @Override
            public void onInit() {
                mListView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    CateBrandGoodsListBean brands = (CateBrandGoodsListBean) data;
                    if (brands != null){
                        mListView.onCategryBrandSucessFul(brands);
                        pageIndex++;
                    }else {
                        mListView.onEmptyData();
                    }
                }else {
                    mListView.onCategryBrandFailure("返回的数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mListView.hideLoading();
                mListView.onCategryBrandFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mListView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {
        mParams.put("pageIndex",String.valueOf(pageIndex));
        ApiManager.getCategryLists(mParams).subscribe(new SubScribeCallBack<CateBrandGoodsListBean>(new CallBack() {
            @Override
            public void onInit() {
                mListView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    CateBrandGoodsListBean brands = (CateBrandGoodsListBean) data;
                    List<GoodsListBean> goodsList = brands.getGoodsList();
                    if (goodsList!= null && goodsList.size() >0){
                        mListView.loadMoreData(brands);
                        pageIndex++;
                    }else {
                        mListView.loadNoMore();
                    }
                }else {
                    mListView.onCategryBrandFailure("返回的数据为NULL",0);
                }
            }

            @Override
            public void onFailure(ExceptionHandle.ResponeThrowable t) {
                mListView.hideLoading();
                mListView.onCategryBrandFailure(t.getMessage(),t.getCode());
            }

            @Override
            public void onCompleted() {
                mListView.hideLoading();
            }
        }));
    }

    @Override
    public void setCategryBrandData(Map<String, String> params) {
        this.mParams = params;
    }
}
