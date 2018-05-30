package com.koalafield.cmart.presenter.search;

import com.jrm.retrofitlibrary.callback.CallBack;
import com.jrm.retrofitlibrary.callback.SubScribeCallBack;
import com.koalafield.cmart.api.ApiManager;
import com.koalafield.cmart.bean.search.SearchListBean;
import com.koalafield.cmart.ui.view.search.ISearchView;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangrenming on 2018/5/31.
 */

public class SearchPresenter implements ISearchPresenter{


    private ISearchView mSearchView;
    private Map<String,String> mParams;
    private int indexPage = 0;

    public SearchPresenter(ISearchView sercher){
        this.mSearchView = sercher;
    }

    @Override
    public void getData() {
        mParams.put("pageIndex",String.valueOf(0));
        ApiManager.getserchList(mParams).subscribe(new SubScribeCallBack<List<SearchListBean>>(new CallBack() {
            @Override
            public void onInit() {
                mSearchView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    List<SearchListBean> searchListBeen = (List<SearchListBean>) data;
                    if (searchListBeen != null && searchListBeen.size() > 0){
                        mSearchView.onSearchSucessFul(searchListBeen);
                        indexPage++;
                    }else {
                        mSearchView.onEmptyData();
                    }
                }else {
                    mSearchView.onSearchFailure("返回数据为NULL");

                }
            }

            @Override
            public void onFailure(Throwable t) {
                mSearchView.hideLoading();
                mSearchView.onSearchFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mSearchView.hideLoading();
            }
        }));
    }

    @Override
    public void getMoreData() {

        mParams.put("pageIndex",String.valueOf(indexPage));
        ApiManager.getserchList(mParams).subscribe(new SubScribeCallBack<List<SearchListBean>>(new CallBack() {
            @Override
            public void onInit() {
                mSearchView.showLoading();
            }

            @Override
            public <T> void onSucess(T data) {
                if (data != null){
                    List<SearchListBean> searchListBeen = (List<SearchListBean>) data;
                    if (searchListBeen != null && searchListBeen.size() > 0){
                        mSearchView.loadMoreData(searchListBeen);
                        indexPage++;
                    }else {
                        mSearchView.loadNoMoreData();
                    }
                }else {
                    mSearchView.onSearchFailure("返回数据为NULL");

                }
            }

            @Override
            public void onFailure(Throwable t) {
                mSearchView.hideLoading();
                mSearchView.onSearchFailure(t.getMessage());
            }

            @Override
            public void onCompleted() {
                mSearchView.hideLoading();
            }
        }));
    }

    @Override
    public void setParams(Map<String, String> params) {
        mParams = params;
    }
}
